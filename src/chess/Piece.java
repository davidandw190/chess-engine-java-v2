package chess;

import java.util.ArrayList;

public abstract class Piece {

    public String name;
    public String symbol;

    public String movementPattern;
    public int value;
    public int row;
    public int column;
    public char color;
    public boolean hasMoved;

    public Piece(char color) {
        this.color = color;
        this.hasMoved = false;
    }

    public Piece(String name, String symbol, char color) {
        this.name = name;
        this.symbol = symbol;
        this.color = color;
        this.hasMoved = false;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setSymbol(String symbol){
        this.symbol= symbol;
    }

    // abstract methods

    public abstract ArrayList<int[]> legalMoves();

    public abstract void move(char piece, char[] endPosition);

    public char getOppositeColor() {
        if (this.color != ' ') {
            return (this.color == 'w') ? 'b' : 'w';
        }
        return ' ';
    }


    protected void calculateLegalMovesInDirection(ArrayList<int[]> legalMoves, char oppositeColor, int[] direction) {
        int legalRow = this.row + direction[0];
        int legalColumn = this.column + direction[1];

        while (isValidPosition(legalRow, legalColumn )) {
            int[] coordinates = {legalRow, legalColumn };
            Piece piece = Board.boardPieces.get(Board.findPositionByLocation(coordinates));

            if (piece.color == color) {
                break;
            } else if (piece.color == oppositeColor) {
                legalMoves.add(coordinates);
                break;
            } else {
                legalMoves.add(coordinates);
            }

            legalRow += direction[0];
            legalColumn += direction[1];
        }
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }







}
