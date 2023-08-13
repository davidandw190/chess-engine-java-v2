package chess.pieces;

import chess.Piece;

import java.util.ArrayList;

/**
 * Represents a Queen chess piece.
 */
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
        ArrayList<int []> legalMoves = new ArrayList<>();
        char oppositeColor = this.getOppositeColor();

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direction: directions) {
            calculateLegalMovesInDirection(legalMoves, oppositeColor, direction);
        }

        return legalMoves;
    }


    @Override
    public void move(char piece, char[] endPosition) {

    }
}
