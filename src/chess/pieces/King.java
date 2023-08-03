package chess.pieces;

import chess.Piece;

import java.util.ArrayList;

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
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
