package chess;

import chess.pieces.*;

import java.util.*;

/**
 * The Board class represents the chessboard and its operations.
 * It manages the arrangement of pieces, legal moves, and check conditions.
 */
public class Board {

    private static final int BOARD_SIZE = 8;

    /* All the pieces on the chessboard */
    public static ArrayList<Piece> boardPieces = new ArrayList<>();


    /**
     * Retrieves the list of white pieces from the current board configuration.
     *
     * @return  An ArrayList containing all white pieces on the board.
     */
    public static ArrayList<Piece> getWhitePieces() {
        System.out.println("get white pieces was called");
        return getPiecesByColor('w');
    }


    /**
     * Retrieves the list of black pieces from the current board configuration.
     *
     * @return  An ArrayList containing all black pieces on the board.
     */
    public static ArrayList<Piece> getBlackPieces() {
        System.out.println("get black pieces was called");
        return getPiecesByColor('b');
    }


    /**
     * initializes the chessboard based on the given FEN notation.
     *
     * It parses the FEN notation to initialize the positions of pieces on the board.
     * It processes each character in the FEN string to create the corresponding Piece objects and places them
     * in the allPieces list, updating their positions accordingly.
     *
     * @param pattern   The FEN notation representing the initial board configuration.
     */
    public static void initializeBoard(String pattern) {
        boardPieces.clear();

        initializePiecesFromPattern(pattern);

        for (int i = 0; i < boardPieces.size(); i++) {
            Piece piece = boardPieces.get(i);
            int[] coordinates = findCoordinates(i);
            piece.setPosition(coordinates[0], coordinates[1]);
        }
    }

    /**
     * Moves a piece on the board to the specified target position and updates the board accordingly.
     *
     * @param board             The current state of the chessboard.
     * @param pieceIndex        The index of the piece to be moved.
     * @param targetPosition    The target position where the piece is to be moved.
     * @throws IllegalArgumentException If the piece index is invalid or the target position is invalid.
     */
    public static void movePiece(ArrayList<Piece> board, int pieceIndex, int[] targetPosition) {
        Piece pieceToMove = board.get(pieceIndex);
        int finalPositionIndex = findPositionByLocation(targetPosition);

        if (pieceToMove == null || finalPositionIndex == -1) {
            throw new IllegalArgumentException("Invalid piece index or final position");
        }

        Piece targetPiece = board.get(finalPositionIndex);
        int[] initialCoordinates = findCoordinates(pieceIndex);

        EmptySquare emptySquare = (EmptySquare) targetPiece;
        Collections.swap(board, pieceIndex, finalPositionIndex);

        emptySquare.setPosition(initialCoordinates[0], initialCoordinates[1]);
        pieceToMove.setPosition(targetPosition[0], targetPosition[1]);
    }



    /**
     * Finds the attack moves available for the given chess piece.
     *
     * It computes and returns a list of attack moves for the specified piece,
     * taking into account the piece's type and current board configuration. For pawn pieces,
     * the method returns null since pawn attacks are handled differently.
     *
     * @param piece     The chess piece for which to find attack moves.
     * @return          An ArrayList of attack move coordinates or null for pawn pieces.
     */
    public static ArrayList<int[]> findAttacks(Piece piece){
            return findLegalMoves(piece);
    }


    /**
     * Filters legal moves to exclude self-check positions for a given piece.
     *
     * @param legalMoves   The list of legal moves to filter.
     * @param indexPiece   The index of the piece making the moves.
     * @param turn         The current turn's color.
     * @return A list of legal moves excluding self-check positions.
     */
    public static ArrayList<int[]> excludeSelfChecks(ArrayList<int[]> legalMoves, int indexPiece, int turn) {
        /* Board copy to avoid modifications to the actual board while we simulate the moves */
        ArrayList<Piece> originalPositions = new ArrayList<>(boardPieces);
        ArrayList<int[]> verifiedLegalMoves = new ArrayList<>();

        Piece currentPiece = originalPositions.get(indexPiece);

        if (currentPiece.getName().equals("King")) {
            char enemyColor = (currentPiece.getColor() == 'w') ? 'b' : 'w';
            /* TODO incorporate castling */
        }

        if (legalMoves.isEmpty()) {
            return verifiedLegalMoves;
        }

        for (int[] legalMove : legalMoves) {
            ArrayList<Piece> simulatedPositions = new ArrayList<>(originalPositions);
            int indexLegal = findPositionByLocation(legalMove);

            boolean capture = moveOrCapture(simulatedPositions, legalMove, indexPiece, indexLegal);

            if (!isCheck(simulatedPositions, turn)) {
                verifiedLegalMoves.add(legalMove);
            }

//            undoMoveOrCapture(simulatedPositions, indexPiece, indexLegal, capture);
        }

        return verifiedLegalMoves;
    }

    private static boolean moveOrCapture(ArrayList<Piece> simulatedPositions, int[] legalMove, int indexPiece, int indexLegal) {
        Piece targetPiece = simulatedPositions.get(indexLegal);

        if (targetPiece.getName().equals("Empty")){
            movePiece(simulatedPositions, indexPiece, legalMove);
            return false;
        } else {
            capture(simulatedPositions, indexPiece, legalMove);
            return true;
        }

    }

//    private static void undoMoveOrCapture(ArrayList<Piece> simulatedPositions, int indexPiece, int indexLegal, boolean capture) {
//        /* undo the move if needed */
//    }


    /**
     * Moves a piece to a new position and captures the opponent's piece if present.
     *
     * @param board             The chessboard represented as a list of pieces.
     * @param pieceIndex        The index of the piece to be moved.
     * @param finalPosition     The final position (row, column) to move the piece to.
     */
    public static void capture(ArrayList<Piece> board, int pieceIndex, int[] finalPosition) {
        int finalPositionIndex = findPositionByLocation(finalPosition);
        EmptySquare emptySquare = new EmptySquare(finalPosition[0], finalPosition[1]);

        board.set(finalPositionIndex, emptySquare);

        movePiece(board, pieceIndex, finalPosition);
    }


    /**
     * Checks if the current player is in checkmate.
     *
     * @param currentBoard      The current state of the chessboard.
     * @param turn              The current turn number.
     * @return True if the current player is in checkmate, false otherwise.
     */
    public static boolean checkForCheckMate(ArrayList<Piece> currentBoard, int turn) {
        return checkForEndgameCondition(currentBoard, turn, true);
    }


    /**
     * Checks if the current player is in stalemate.
     *
     * @param currentBoard      The current state of the chessboard.
     * @param turn              The current turn number.
     * @return True if the current player is in checkmate, false otherwise.
     */
    public static boolean checkForStalemate(ArrayList<Piece> currentBoard, int turn) {
        return checkForEndgameCondition(currentBoard, turn, false);
    }


    public static boolean isCheck(ArrayList<Piece> board, int turn) {
        ArrayList<Piece> alliedPieces;
        ArrayList<Piece> opponentPieces;
        Piece alliedKing;

        if (turn % 2 == 0) {
            alliedPieces = getBlackPieces();
            opponentPieces = getWhitePieces();
            alliedKing = findKing('b', alliedPieces);
        } else {
            alliedPieces = getWhitePieces();
            opponentPieces = getBlackPieces();
            alliedKing = findKing('w', alliedPieces);
        }

        return isAttacked(alliedKing, opponentPieces);
    }


    

    /**
     * Checks if a piece is attacked by any of the specified attackers.
     *
     * @param piece The piece to check for being attacked.
     * @param attackers The list of pieces that could potentially attack the given piece.
     * @return {@code true} if the piece is attacked by any of the attackers, otherwise {@code false}.
     */
    public static boolean isAttacked(Piece piece, ArrayList<Piece> attackers) {
        for (Piece attacker : attackers) {
            ArrayList<int[]> attacks = findAttacks(attacker);
            for (int[] attack : attacks) {
                if (attack[0] == piece.getRow() && attack[1] == piece.getColumn()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printBoard(ArrayList<Piece> boardPieces) {
        System.out.println();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int index = findPositionByLocation(new int[]{row, col});
                Piece piece = boardPieces.get(index);
                if (piece.getName().equals("Empty")) {
                    System.out.print(". ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    public static int findPositionByLocation(int[] coords) {
        return (coords[0] * BOARD_SIZE) + coords[1];
    }

    public static int[] findCoordinates(int location) {
        int row = location / BOARD_SIZE;
        int column = location % BOARD_SIZE;
        return new int[] {row, column};
    }

    private static void initializePiecesFromPattern(String pattern) {
        for (char c : pattern.toCharArray()) {
            if (c == '/') {
                continue;        /* Skip separator character */
            }

            if (Character.isDigit(c)) {
                int emptySquares = Character.getNumericValue(c);
                for (int j = 0; j < emptySquares; j++) {
                    boardPieces.add(new EmptySquare());
                }
            } else {
                char color = Character.isUpperCase(c) ? 'w' : 'b';
                char pieceType = Character.toLowerCase(c);

                switch (pieceType) {
                    case 'p' -> boardPieces.add(new Pawn(color));
                    case 'r' -> boardPieces.add(new Rook(color));
                    case 'n' -> boardPieces.add(new Knight(color));
                    case 'b' -> boardPieces.add(new Bishop(color));
                    case 'q' -> boardPieces.add(new Queen(color));
                    case 'k' -> boardPieces.add(new King(color));
                    default -> throw new IllegalArgumentException("Invalid FEN character : " + pieceType);
                }

            }
        }
    }

    /**
     * Finds the legal moves available for the given piece.
     *
     * It determines and returns a list of legal move coordinates for the specified piece,
     * based on its type and the current board configuration. It delegates to specific methods
     * for each piece type to compute their individual legal moves.
     *
     * @param piece     The chess piece for which to find legal moves.
     * @return          An ArrayList of legal move coordinates.
     */
    private static ArrayList<int[]> findLegalMoves(Piece piece) {
        ArrayList<int[]> legalMoves;

        switch (piece.getName()) {
            case "Pawn" -> {
                Pawn thisPawn = (Pawn) piece;
                legalMoves = thisPawn.legalMoves();
            }
            case "Knight" -> {
                Knight thisKnight = (Knight) piece;
                legalMoves = thisKnight.legalMoves();
            }
            case "Bishop" -> {
                Bishop thisBishop = (Bishop) piece;
                legalMoves = thisBishop.legalMoves();
            }
            case "Rook" -> {
                Rook thisRook = (Rook) piece;
                legalMoves = thisRook.legalMoves();
            }
            case "Queen" -> {
                Queen thisQueen = (Queen) piece;
                legalMoves = thisQueen.legalMoves();
            }
            case "King" -> {
                King thisKing = (King) piece;
                legalMoves = thisKing.legalMoves();
            }
            default -> {
                return null;
            }
        }

        return legalMoves;
    }

    private static boolean isLegalMoveAvoidingCheck(int indexPiece, int[] legalMove, ArrayList<Piece> originalPositions, int turn) {
        ArrayList<Piece> possiblePositions = new ArrayList<>(originalPositions);
        int indexLegal = findPositionByLocation(legalMove);

        Piece capturedSquare = possiblePositions.get(indexLegal);
        boolean isThereCapturedPiece = (capturedSquare.getColor() != ' ');

        if (isThereCapturedPiece) {
            possiblePositions.set(indexLegal, new EmptySquare());
        }

        System.out.println("OG positions");
        printBoard(originalPositions);

        System.out.println("Possible positions");
        printBoard(possiblePositions);

        possiblePositions.set(indexPiece, new EmptySquare());
        movePiece(possiblePositions, indexPiece, legalMove);

        return !isCheck(possiblePositions, turn);
    }

    private static boolean checkForEndgameCondition(ArrayList<Piece> currentBoard, int turn, boolean checkmate) {
        ArrayList<Piece> alliedPieces = (turn % 2 == 0) ? getBlackPieces() : getWhitePieces();

        for (Piece alliedPiece : alliedPieces) {
            ArrayList<int[]> pieceLegalMoves = alliedPiece.legalMoves();
            int[] location = {alliedPiece.getRow(), alliedPiece.getColumn()};
            int indexPiece = findPositionByLocation(location);
            ArrayList<int[]> curatedLegalMoves = excludeSelfChecks(pieceLegalMoves, indexPiece, turn);

            if (curatedLegalMoves.isEmpty()) {
                return checkmate ? !isCheck(currentBoard, turn) : true;
            }
        }

        return checkmate ? isCheck(currentBoard, turn) : false;
    }

    private static ArrayList<Piece> getPiecesByColor(char color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece piece : boardPieces) {
            if (piece.getColor() == color) {

                System.out.println(piece.name + "-should be `" + color + "` and is :  " + piece.color);
                pieces.add(piece);
            }
        }
        return pieces;
    }

    private static Piece findKing(char color, ArrayList<Piece> boardPieces) {
        for (Piece piece : boardPieces) {
            if ((piece.getName().equals("King")) && (piece.getColor() == color)) {
                return piece;
            }
        }
        throw new IllegalStateException(String.format("No king found for color `%s`", color));
    }

}
