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
       ArrayList<int[]> legalMoves = new ArrayList<>();
       char oppositeColor = (this.color == 'w') ? 'b' : 'w';
       int [][] directions = {{-1,-1}, {-1, 1}, {1, -1}, {1, 1}};

       for (int[] direction : directions) {
           calculateLegalMovesInDirection(legalMoves, oppositeColor, direction);
       }
        return legalMoves;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
