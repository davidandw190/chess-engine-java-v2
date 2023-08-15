package chess;

import chess.pieces.*;

import java.util.*;

/**
 * The Board class represents the chessboard and its operations.
 * It manages the arrangement of pieces, legal moves, and check conditions.
 */
public class Board {

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

        switch (piece.name) {
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


    public static ArrayList<int[]> excludeSelfChecks(ArrayList<int[]> legalMoves, int indexPiece, double turn) {
        ArrayList<Piece> originalPositions = boardPieces;
        ArrayList<int[]> checkedLegalMoves = new ArrayList<>();

        Piece piece = boardPieces.get(indexPiece);
        char oppositeColor = piece.getOppositeColor();

        for (int[] legalMove : legalMoves) {
            if (isLegalMoveAvoidingCheck(indexPiece, legalMove, originalPositions, oppositeColor)) {
                checkedLegalMoves.add(legalMove);
            }
        }

        return checkedLegalMoves;
    }

    private static boolean isLegalMoveAvoidingCheck(int indexPiece, int[] legalMove, ArrayList<Piece> originalPositions, char oppositeColor) {

        /* copy of the original positions on the board at that particular point in time, used for simulating the move */
        ArrayList<Piece> possiblePositions = new ArrayList<>(originalPositions);
        int indexLegal = findPositionByLocation(legalMove);
        Piece currentPiece = boardPieces.get(indexPiece);

        Piece capturedSquare = possiblePositions.get(indexLegal);
        boolean isThereCapturedPiece = (capturedSquare.getColor() != ' ');

        if (isThereCapturedPiece) {
            possiblePositions.set(indexLegal, new EmptySquare());
        }

        possiblePositions.set(indexPiece, new EmptySquare());

        movePiece(possiblePositions, indexPiece, legalMove);

        /* we check if the king's position is not in check after the move. */
        boolean legalMoveAvoidsCheck = !isInCheck(possiblePositions, currentPiece.getColor());

        /* we restore the original positions of the pieces after the move simulation */
        if (legalMoveAvoidsCheck) {
            possiblePositions.set(indexLegal, capturedSquare);
        }
        possiblePositions.set(indexPiece, boardPieces.get(indexPiece));

        return legalMoveAvoidsCheck;
    }

    private static void movePiece(ArrayList<Piece> board, int indexPiece, int[] finalPosition) {
        Piece piece = board.get(indexPiece);
        board.set(findPositionByLocation(finalPosition), piece);
        piece.setPosition(finalPosition[0], finalPosition[1]);
    }

    private static boolean isInCheck(ArrayList<Piece> currentPositions, char color) {


        Piece king = findKing(color, currentPositions);

        ArrayList<Piece> oppositePieces = (color == 'w') ? getBlackPieces() : getWhitePieces();

        for (Piece oppositePiece : oppositePieces) {
            ArrayList<int[]> attacks = findAttacks(oppositePiece);

            for (int[] attack : attacks) {
                if ((attack[0] == king.getRow()) && (attack[1] == king.getColumn())) {
                    return true;
                }
            }

        }

        return false;
    }

    public static int findPositionByLocation(int[] coords) {
        return (8 * coords[0]) + coords[1];
    }


    public static int[] findCoordinates(int location) {
        int row = (int) Math.floor(location / 8.0);
        int column = location % 8;
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
            if (piece.name.equals("King") && piece.color == color) {
                return piece;
            }
        }

        System.out.printf("King for color `%s` could not be found for some reason!!", color);
        return new EmptySquare();
    }


}
