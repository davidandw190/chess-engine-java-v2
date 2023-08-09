package chess.pieces;

import chess.Board;
import chess.Piece;


import javax.swing.*;
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
           int legalRow = this.row + direction[0];
           int legalColumn = this.row + direction[1];

           while (legalRow >= 0 && legalRow < 8 && legalColumn >= 0 && legalColumn < 8) {
               int[] coords = {legalRow, legalColumn};
               int position = Board.findPositionByLocation(coords);
               Piece currentLegalPiece = Board.boardPieces.get(position);

               if (currentLegalPiece.color == this.color) {
                   break;
               } else if (currentLegalPiece.color == oppositeColor) {
                    legalMoves.add(coords);
                    break;
               } else {
                   legalMoves.add(coords);
               }

               legalRow += direction[0];
               legalColumn += direction[1];
           }

       }
        return legalMoves;
    }

    @Override
    public void move(char piece, char[] endPosition) {

    }
}
