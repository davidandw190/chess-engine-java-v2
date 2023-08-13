package chess.pieces;

import chess.Piece;

import java.util.ArrayList;

/**
 * Represents an unoccupied square on the chess board.
 */
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
        return new ArrayList<>();
    }

}
