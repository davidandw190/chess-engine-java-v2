package chess.pieces;

import chess.Piece;

import java.util.ArrayList;

/**
 * Represents a Rook chess piece.
 */
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
        ArrayList<int[]> legalMoves = new ArrayList<>();
        char oppositeColor = this.getOppositeColor();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            calculateLegalMovesInDirection(legalMoves, oppositeColor, direction);
        }

        return legalMoves;
    }


    @Override
    public void move(char piece, char[] endPosition) {

    }
}
