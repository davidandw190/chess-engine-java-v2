package chess.pieces;

import chess.Piece;

import java.util.ArrayList;


public class Rook extends Piece {

    public Rook(int row, int column, char color) {
        super("Rook", (color == 'w') ? "r" : "R", color);
        this.movementPattern = "straight";
        this.value = 5;
        this.row = row;
        this.column = column;
    }

    public Rook(char color) {
        super("Rook", (color == 'w') ? "r" : "R", color);
        this.movementPattern = "straight";
        this.value = 5;
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
