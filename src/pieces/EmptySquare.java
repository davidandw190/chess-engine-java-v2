package pieces;

public class EmptySquare extends Piece {

    public EmptySquare(int row, int column) {
        this.name="Empty";
        this.symbol=" ";
        this.row=row;
        this.column=column;
        this.color=' ';
    }

    public EmptySquare(){
        this.name="Empty";
        this.symbol=" ";
        this.color=' ';
    }

    @Override
    public void move(char piece, char[] endPosition) {}
}
