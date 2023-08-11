package chess.pieces;

import chess.Board;
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
//
//    private void calculateLegalMovesInDirection(ArrayList<int[]> legalMoves, char oppositeColor, int[] direction) {
//        int legalRow = direction[0];
//        int legalColumn = direction[1];
//
//        while (isValidPosition(legalRow, legalColumn)) {
//            int[] coordinates = {legalRow, legalColumn};
//            Piece occupyingPiece = Board.boardPieces.get(Board.findPositionByLocation(coordinates));
//
//            if (occupyingPiece.color == this.color) {
//                break;
//
//            } else if (occupyingPiece.color == oppositeColor) {
//                legalMoves.add(coordinates);
//                break;
//            } else {
//                legalMoves.add(coordinates);
//            }
//
//            legalRow += direction[0];
//            legalColumn += direction[1];
//        }
//    }


    @Override
    public void move(char piece, char[] endPosition) {

    }
}
