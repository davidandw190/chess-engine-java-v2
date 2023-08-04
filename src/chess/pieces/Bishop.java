package chess.pieces;

import chess.Piece;


import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int row, int column, char color) {
        super("Bishop", (color == 'w') ? "b" : "B", color);
        this.movementPattern = "diagonal";
        this.value = 3;
        this.row = row;
        this.column = column;
    }

    public Bishop(char color) {
        super("Bishop", (color == 'w') ? "b" : "B", color);
        this.movementPattern = "diagonal";
        this.value = 3;
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}