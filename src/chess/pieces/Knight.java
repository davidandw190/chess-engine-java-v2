package chess.pieces;

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
        this.row = row;
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
