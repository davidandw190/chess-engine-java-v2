package chess.pieces;

import chess.Board;
import chess.Piece;

import java.util.ArrayList;

/**
 * Represents a King chess piece.
 */
public class King extends Piece {

    public int[] startingPosition;

    public King(int row, int column, char color) {
        super("King", (color == 'w') ? "k" : "K", color);
        this.movementPattern = "adjacent";
        this.value = 1000;
        this.row = row;
        this.column = column;
        this.setStartingPosition();
    }

    public King(char color) {
        super("King", (color == 'w') ? "k" : "K", color);
        this.movementPattern = "adjacent";
        this.value = 1000;
        this.setStartingPosition();
    }

    private void setStartingPosition() {
        if (this.color == 'w') {
            this.startingPosition = new int[]{7, 4};
        } else {
            this.startingPosition = new int[]{0, 4};
        }

        if (this.column != startingPosition[1] || this.row != startingPosition[0]) {
            this.hasMoved = true;
        }
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        ArrayList<int[]> legalMoves = new ArrayList<>();
        int[][] possibleDirections = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direction : possibleDirections) {
            calculateLegalMovesInDirection(legalMoves, getOppositeColor(), direction);
        }

        return legalMoves;
    }

    @Override
    protected void calculateLegalMovesInDirection(ArrayList<int[]> legalMoves, char oppositeColor, int[] direction) {
        int newPositionRow = this.row + direction[0];
        int newPositionColumn = this.column + direction[1];

        if (isValidPosition(newPositionRow, newPositionColumn)) {
            int[] coords = {newPositionRow, newPositionColumn};
            Piece targetPiece = Board.boardPieces.get(Board.findPositionByLocation(coords));

            if (targetPiece.color != this.color) {
                legalMoves.add(coords);
            }
        }
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
