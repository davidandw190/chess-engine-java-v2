package chess;


import java.util.ArrayList;

/**
 * Abstract class representing a chess piece.
 */
public abstract class Piece {
    protected String name;
    protected String symbol;
    protected String movementPattern;
    protected int value;
    protected int row;
    protected int column;
    protected char color;
    protected boolean hasMoved;

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

    /**
     * Abstract method to calculate legal moves for the piece.
     *
     * @return An ArrayList of legal move coordinates.
     */
    public abstract ArrayList<int[]> legalMoves();

    /**
     * Gets the color of the opposite player.
     *
     * @return The opposite color.
     */
    public char getOppositeColor() {
        if (this.color != ' ') {
            return (this.color == 'w') ? 'b' : 'w';
        }
        return ' ';
    }

    /**
     * Calculates legal moves in a specific direction and adds them to the list.
     *
     * @param legalMoves The list to add legal moves to.
     * @param oppositeColor The opposite color.
     * @param direction The direction to calculate moves in.
     */
    protected void calculateLegalMovesInDirection(ArrayList<int[]> legalMoves, char oppositeColor, int[] direction) {
        int possiblePositionRow = this.row + direction[0];
        int possiblePositionColumn = this.column + direction[1];

        while (isValidPosition(possiblePositionRow, possiblePositionColumn)) {
            int[] coordinates = {possiblePositionRow, possiblePositionColumn};
            Piece piece = Board.boardPieces.get(Board.findPositionByLocation(coordinates));

            if (piece.color == color) {
                break;
            } else if (piece.color == oppositeColor) {
                legalMoves.add(coordinates);
                break;
            } else {
                legalMoves.add(coordinates);
            }

            possiblePositionRow += direction[0];
            possiblePositionColumn += direction[1];
        }
    }

    /**
     * Checks if a given position is within the board bounds.
     *
     * @param row The row position.
     * @param column The column position.
     * @return true if the position is valid, false otherwise.
     */
    protected boolean isValidPosition(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
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
        this.symbol = symbol;
    }

    public char getColor() {
        return this.color;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMovementPattern() {
        return movementPattern;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }
}
