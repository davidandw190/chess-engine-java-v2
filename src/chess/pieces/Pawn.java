package chess.pieces;

import chess.Board;
import chess.Piece;

import java.util.ArrayList;


public class Pawn extends Piece {


    public static int promotion = -1;

    public Pawn(int row, int column, char color) {
        super("Pawn", (color == 'w') ? "p" : "P", color);
        this.movementPattern = "forward";
        this.value = 1;
        this.row = row;
        this.column = column;
    }

    public Pawn(char color) {
        super("Pawn", (color == 'w') ? "p" : "P", color);
        this.movementPattern = "forward";
        this.value = 1;
    }

    @Override
    public ArrayList<int[]> legalMoves() {

        int[] coords= {this.row, this.column};
        return null;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
