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
        return getPiecesByColor('w');
    }


    /**
     * Retrieves the list of black pieces from the current board configuration.
     *
     * @return  An ArrayList containing all black pieces on the board.
     */
    public static ArrayList<Piece> getBlackPieces() {
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
        for (int i = 0; i < pattern.length(); i++) {

            char c = pattern.charAt(i);
            int n = (int) c;

            switch (c) {
                case 'r' -> boardPieces.add(new Rook('b'));
                case 'n' -> boardPieces.add(new Knight('b'));
                case 'b' -> boardPieces.add(new Bishop('b'));
                case 'q' -> boardPieces.add(new Queen('b'));
                case 'k' -> boardPieces.add(new King('b'));
                case 'p' -> boardPieces.add(new Pawn('b'));

                case 'R' -> boardPieces.add(new Rook('w'));
                case 'N' -> boardPieces.add(new Knight('w'));
                case 'B' -> boardPieces.add(new Bishop('w'));
                case 'Q' -> boardPieces.add(new Queen('w'));
                case 'K' -> boardPieces.add(new King('w'));
                case 'P' -> boardPieces.add(new Pawn('w'));
                case '/' -> {} // Do nothing for '/'

                default -> {
                    /* to handle numeric characters (ASCII 48-57) */
                    if (Character.isDigit(c)) {

                        int emptySquareCount = c - '0';
                        for (int j = 0; j < emptySquareCount; j++) {
                            boardPieces.add(new EmptySquare());
                        }
                    }
                }
            }
        }

        for(int i=0; i<boardPieces.size();i++){
            Piece piece = boardPieces.get(i);
            int[] coordinates = findCoordinates(i);
            piece.setPosition(coordinates[0], coordinates[1]);
        }
    }


    /**
     * Moves a piece to the specified final position on the board.
     *
     * It performs the movement of a piece to a given position on the board,
     * handling piece swaps and updating their positions accordingly.
     *
     * @param boardState             The current board configuration.
     * @param indexPiece        The index of the piece in the boardPieces list.
     * @param finalPosition     The final position to move the piece to.
     */
    public static void move(ArrayList<Piece> boardState, int indexPiece, int[] finalPosition) {
        Piece piece = boardState.get(indexPiece);
        int indexFinal = findPositionByLocation(finalPosition);
        EmptySquare emptySquare = (EmptySquare) boardState.get(indexFinal);
        Collections.swap(boardState, indexPiece, indexFinal);
        int[] initial = findCoordinates(indexPiece);

        emptySquare.setPosition(initial[0], initial[1]); //
        piece.setPosition(finalPosition[0], finalPosition[1]);

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


    /**
     * Excludes moves that would result in self-check situations.
     *
     * @param legalMoves     The list of legal moves for a piece.
     * @param indexPiece     The index of the piece in the boardPieces list.
     * @param turn           The current turn (used for determining the player's color).
     * @return               A filtered list of legal moves avoiding self-check.
     */
    public static ArrayList<int[]> excludeSelfChecks(ArrayList<int[]> legalMoves, int indexPiece, double turn) {
        ArrayList<Piece> originalPositions = boardPieces;
        ArrayList<int[]> checkedLegalMoves = new ArrayList<>();

        for (int[] legalMove : legalMoves) {
            if (isLegalMoveAvoidingCheck(indexPiece, legalMove, originalPositions, turn)) {
                checkedLegalMoves.add(legalMove);
            }
        }

        return checkedLegalMoves;
    }


    /**
     * Checks if the current player is in checkmate.
     *
     * @param currentPosition The current state of the chessboard.
     * @param turn The current turn number.
     * @return {@code true} if the current player is in checkmate, otherwise {@code false}.
     */
    public static boolean checkForCheckMate(ArrayList<Piece> currentPosition, double turn) {
        ArrayList<Piece> outPieces = (turn % 1 == 0 ) ? getWhitePieces() : getBlackPieces();

        for (Piece piece : outPieces) {
            ArrayList<int[]> pieceLegalMoves = findLegalMoves(piece);
            int[] location  = {piece.getRow(), piece.getColumn()};
            int indexPiece = findPositionByLocation(location);
            assert pieceLegalMoves != null;
            ArrayList<int[]> remainingPieceLegalMoves = excludeSelfChecks(pieceLegalMoves, indexPiece, turn);

            if (!remainingPieceLegalMoves.isEmpty()) {
                return false;
            }
        }
        return isCheck(currentPosition, turn);
    }


    /**
     * Checks if the current player is in a stalemate position.
     *
     * @param currentPosition The current state of the chessboard.
     * @param turn The current turn number.
     * @return {@code true} if the current player is in a stalemate position, otherwise {@code false}.
     */
    public static boolean checkForStalemate(ArrayList<Piece> currentPosition, double turn) {
        ArrayList<Piece> ourPieces = (turn % 1 == 0) ? getWhitePieces() : getBlackPieces();

        for (Piece piece : ourPieces) {
            ArrayList<int[]> pieceLegalMoves = findLegalMoves(piece);
            int[] location = { piece.getRow(), piece.getColumn() };
            int indexPiece = findPositionByLocation(location);

            assert pieceLegalMoves != null;

            ArrayList<int[]> remainingPieceLegalMoves = excludeSelfChecks(pieceLegalMoves, indexPiece, turn);

            if (!remainingPieceLegalMoves.isEmpty()) {
                return false;
            }
        }

        return !isCheck(currentPosition, turn);
    }


    /**
     * Checks if a move is legal and avoids putting the player's king in check.
     *
     * @param indexPiece        The index of the piece making the move.
     * @param legalMove         The legal move coordinates.
     * @param originalPositions The original board configuration.
     * @param turn              The current turn (used for determining the player's color).
     * @return                  True if the move is legal and avoids check, false otherwise.
     */
    private static boolean isLegalMoveAvoidingCheck(int indexPiece, int[] legalMove, ArrayList<Piece> originalPositions, double turn) {
        /* Copy of the original positions on the board at that particular point in time, used for simulating the move */
        ArrayList<Piece> possiblePositions = new ArrayList<>(originalPositions);
        int indexLegal = findPositionByLocation(legalMove);

        Piece capturedSquare = possiblePositions.get(indexLegal);
        boolean isThereCapturedPiece = (capturedSquare.getColor() != ' ');

        if (isThereCapturedPiece) {
            possiblePositions.set(indexLegal, new EmptySquare());
        }

        possiblePositions.set(indexPiece, new EmptySquare());

        /* Simulating the move and updating positions  on the copy of the board*/
        move(possiblePositions, indexPiece, legalMove);

        /* Check if the king's position is not in check after the move. */
        boolean legalMoveAvoidsCheck = !isCheck(possiblePositions, turn);

        /* Restore the original positions of the pieces after the move simulation */
        if (legalMoveAvoidsCheck) {
            possiblePositions.set(indexLegal, capturedSquare);
        }
        possiblePositions.set(indexPiece, boardPieces.get(indexPiece));

        return legalMoveAvoidsCheck;
    }


    /**
     * Checks if the current player's king is in check.
     *
     * @param currentPositions The current state of the chessboard.
     * @param turn The current turn number.
     * @return {@code true} if the current player's king is in check, otherwise {@code false}.
     */
    public static boolean isCheck(ArrayList<Piece> currentPositions, double turn) {
        ArrayList<Piece> enemyPieces = (turn % 1 == 0) ? getBlackPieces() : getWhitePieces();
        ArrayList<Piece> friendlyPieces = (turn % 1 == 0) ? getWhitePieces() : getBlackPieces();

        Piece alliedKing = findKing(friendlyPieces.get(0).getColor(), currentPositions );

        if (alliedKing == null) {
            return false; // No allied king found for some reason
        }

        return isAttacked(alliedKing, enemyPieces);
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


    public static int findPositionByLocation(int[] coords) {
        return (coords[0] * BOARD_SIZE) + coords[1];
    }

    public static int[] findCoordinates(int location) {
        int row = location / BOARD_SIZE;
        int column = location % BOARD_SIZE;
        return new int[] {row, column};
    }

    private static ArrayList<Piece> getPiecesByColor(char color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Piece piece : boardPieces) {
            if (piece.getColor() == color) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    private static Piece findKing(char color, ArrayList<Piece> boardPieces) {
        for (Piece piece : boardPieces) {
            if ((piece instanceof  King) && (piece.getColor() == color)) {
                return piece;
            }
        }

        System.out.printf("King for color `%s` could not be found for some reason!!", color);
        return null;
    }



}
