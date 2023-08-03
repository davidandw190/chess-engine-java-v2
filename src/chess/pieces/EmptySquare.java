package chess.pieces;

import chess.Piece;

import java.util.ArrayList;

public class EmptySquare extends Piece {

    public EmptySquare(int row, int column) {
        super("Empty", " ", ' ');
        this.row=row;
        this.column=column;
    }

    public EmptySquare() {
        super("Empty", " ", ' ');
    }

    @Override
    public ArrayList<int[]> legalMoves() {
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {}
}
