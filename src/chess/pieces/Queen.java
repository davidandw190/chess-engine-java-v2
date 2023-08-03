package chess.pieces;

import chess.Piece;

import java.util.ArrayList;


public class Queen extends Piece {

    public Queen(int row, int column, char color) {
        super("Queen", (color == 'w') ? "q" : "Q", color);
        this.movementPattern = "queen";
        this.value = 9;
        this.row = row;
        this.column = column;
        this.hasMoved = false;
    }

    public Queen(char color) {
        super("Queen", (color == 'w') ? "q" : "Q", color);
        this.movementPattern = "queen";
        this.value = 9;
        this.hasMoved = false;
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
