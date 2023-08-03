package pieces;

public abstract class Piece {

    public String name;
    public String symbol;

    public String movementPattern;
    public int value;
    public int row;
    public int column;
    public char color;
    public boolean hasMoved;


    public abstract void move(char piece, char[] endPosition);

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setSymbol(String symbol){
        this.symbol= symbol;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }


}
