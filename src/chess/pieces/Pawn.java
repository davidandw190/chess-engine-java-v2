package chess.pieces;

import chess.Board;
import chess.Piece;

import java.util.ArrayList;


public class Pawn extends Piece {


    public static int promotion = -1;

    public Pawn(int row, int column, char color) {
        super("Pawn", (color == 'w') ? "p" : "P", color);
        this.movementPattern = "forward";
        this.value = 1;
        this.row = row;
        this.column = column;
    }

    public Pawn(char color) {
        super("Pawn", (color == 'w') ? "p" : "P", color);
        this.movementPattern = "forward";
        this.value = 1;
    }


    /**
     * Promotes the pawn to a higher-ranked piece if it reaches the opposite end of the board.
     * Displays a GUI with promotion options and updates the board with the chosen piece.
     *
     * @throws InterruptedException If the thread is interrupted while waiting for the promotion choice.
     */
    public void promotion() throws InterruptedException {
        int[] coords = {this.row, this.column};
        int pos = Board.findPositionByLocation(coords);

        int end = (this.color == 'w') ? 0 : 7;

        if (this.row == end) {
            // show prompting options in the gui

            int makeOverPiece = promotion;
            while (makeOverPiece == -1) {
                Thread.sleep(30);
                makeOverPiece = promotion;
            }

            Piece promotedPiece;
            switch (makeOverPiece) {
                case 0 -> promotedPiece = new Queen(this.row, this.column, this.color);
                case 1 -> promotedPiece = new Rook(this.row, this.column, this.color);
                case 2 -> promotedPiece = new Knight(this.row, this.column, this.color);
                case 3 -> promotedPiece = new Bishop(this.row, this.column, this.color);

                default -> promotedPiece = new Queen(this.row, this.column, this.color);
            }

            Board.boardPieces.set(pos, promotedPiece);
            promotion = -1;
            // hide promotion options in the gui
        }

    }

    /**
     * Calculates and returns a list of attack moves for the pawn piece on the chessboard.
     *
     * @return An ArrayList containing the row and column coordinates of attack moves.
     */
    public ArrayList<int[]> attacks() {
        ArrayList<int[]> attacks = new ArrayList<>();

        char oppositeColor = (this.color == 'w') ? 'b' : 'w';
        int moveDirection = (this.color == 'w') ? -1 : 1;

        int [] currentCoords = {this.row, this.column};
        int currentPosition = Board.findPositionByLocation(currentCoords);

        int diagonalLeftPosition = currentPosition + 7 * moveDirection;
        int diagonalRightPosition = currentPosition + 9 * moveDirection;

        handleDiagonalAttack(diagonalLeftPosition, oppositeColor, attacks);
        handleDiagonalAttack(diagonalRightPosition, oppositeColor, attacks);

        return attacks;
    }


    /**
     * Calculates and returns a list of legal moves for the pawn piece on the chessboard.
     *
     * @return An ArrayList containing the row and column coordinates of legal moves.
     */
    @Override
    public ArrayList<int[]> legalMoves() {
        ArrayList<int[]> legalMoves = new ArrayList<>();
        char color = this.color;
        int moveDirection = (color == 'w') ? -1 : 1;

        int[] currentCoords = {this.row, this.column};
        int currentPosition = Board.findPositionByLocation(currentCoords);


        // to handle forward movement for pawns
        int singleStepAhead = currentPosition + 8 * moveDirection;
        if (isWithinBoardBounds(singleStepAhead)) {
            Piece pieceInFront = Board.boardPieces.get(singleStepAhead);

            if (pieceInFront.symbol.equals(" ")) {
                int[] legalMove = {pieceInFront.row, pieceInFront.column};
                legalMoves.add(legalMove);
            }

            // to handle double forward movement (from initial pos) for pawn
            if ((this.row == 6 && color == 'w') || (this.row == 1 && color == 'b')) {
                int doubleStepAhead = currentPosition + 16 * moveDirection;
                Piece pieceTwoAhead = Board.boardPieces.get(doubleStepAhead);
                if (pieceTwoAhead.symbol.equals(" ")) {
                    int[] legalMove2 = {pieceTwoAhead.row, pieceTwoAhead.column};
                    legalMoves.add(legalMove2);
                }
            }
        }

        // to handle diagonal attacks for pawn
        int diagonalLeft = currentPosition + 7 * moveDirection;
        int diagonalRight = currentPosition + 9 * moveDirection;
        handleDiagonalAttack(diagonalLeft, color, legalMoves);
        handleDiagonalAttack(diagonalRight, color, legalMoves);

        // TODO to handle en passant capture
        // ............


        return legalMoves;
    }


    /**
     * Handles diagonal attacks for the pawn and adds legal moves to the list.
     *
     * @param diagonalPosition The position to check for diagonal attack.
     * @param color            The color of the pawn ('w' for white, 'b' for black).
     * @param legalMoves       The ArrayList to store legal moves.
     */
    private void handleDiagonalAttack(int diagonalPosition, char color, ArrayList<int[]> legalMoves) {
        if (isWithinBoardBounds(diagonalPosition)) {
            Piece diagonalPiece = Board.boardPieces.get(diagonalPosition);
            if ( (Math.abs(diagonalPiece.row - this.row) == 1) && (diagonalPiece.color != color)) {
                int[] legalMove = {diagonalPiece.row, diagonalPiece.column};
                legalMoves.add(legalMove);
            }
        }
    }

    /**
     * Checks if the given position is within the bounds of the chessboard.
     *
     * @param position The position to check.
     * @return true if the position is within the board bounds, false otherwise.
     */
    private boolean isWithinBoardBounds(int position) {
        return position >= 0 && position < 64;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
