package chess.pieces;

import chess.Board;
import chess.Piece;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(int row, int column, char color) {
        super("Knight", (color == 'w') ? "h" : "H", color);
        this.movementPattern = "L";
        this.value = 3;
        this.row = row;
        this.column = column;
    }

    public Knight(char color) {
        super("Knight", (color == 'w') ? "h" : "H", color);
        this.movementPattern = "L";
        this.value = 3;
    }

    /**
     * Calculates and returns a list of legal moves for the knight piece on the chessboard.
     *
     * @return An ArrayList containing the row and column coordinates of legal moves.
     */
    @Override
    public ArrayList<int[]> legalMoves() {
        ArrayList<int[]> legalMoves = new ArrayList<>();
        char color = this.color;

        int[] currentCoordinates = {this.row, this.column};
        int currentPosition = Board.findPositionByLocation(currentCoordinates);


        ArrayList<Integer> finalPositions = new ArrayList<>();

        for (int i=-1; i<=1; i+=2) {
            int finalPosition1 = currentPosition + i * 6;
            int finalPosition2 = currentPosition + i * 10;
            int finalPosition3 = currentPosition + i * 15;
            int finalPosition4 = currentPosition + i * 17;

            finalPositions.add(finalPosition1);
            finalPositions.add(finalPosition2);
            finalPositions.add(finalPosition3);
            finalPositions.add(finalPosition4);

        }

        for (int finalPosition : finalPositions) {
            if (isWithinBoardBounds(finalPosition)) {
                Piece atFinal = Board.boardPieces.get(finalPosition);
                if (atFinal.color != color) {
                    int[] legalMove = Board.findCoordinates(finalPosition);
                    if ((Math.abs(legalMove[0] - row) <= 2) && (Math.abs(column - legalMove[1]) <= 2)) {
                        legalMoves.add(legalMove);
                    }
                }
            }
        }

        return legalMoves;
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
