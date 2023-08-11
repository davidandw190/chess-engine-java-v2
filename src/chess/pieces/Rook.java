package chess.pieces;

import chess.Board;
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
        ArrayList<int[]> legalMoves = new ArrayList<>();
        char oppositeColor = this.getOppositeColor();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            calculateLegalMovesInDirection(legalMoves, oppositeColor, direction);
        }

        return legalMoves;
    }
//
//    private void calculateLegalMovesInDirection(ArrayList<int[]> legalMoves, char oppositeColor, int[] direction) {
//        int legalRow = this.row + direction[0];
//        int legalColumn = this.column + direction[1];
//
//        while (isValidPosition(legalRow, legalColumn )) {
//            int[] coordinates = {legalRow, legalColumn };
//            Piece piece = Board.boardPieces.get(Board.findPositionByLocation(coordinates));
//
//            if (piece.color == color) {
//                break;
//            } else if (piece.color == oppositeColor) {
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
//
//    private boolean isValidPosition(int row, int column) {
//        return row >= 0 && row < 8 && column >= 0 && column < 8;
//    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
