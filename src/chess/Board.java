package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Board {

    public static ArrayList<Piece> boardPieces = new ArrayList<>();

    public static ArrayList<Piece> initialiseWhitePieces() {
        ArrayList<Piece> white = new ArrayList<>();

        for (Piece boardPiece : boardPieces) {
            if (boardPiece.color == 'w') {
                white.add(boardPiece);
            }
        }
        return white;
    }

    public static ArrayList<Piece> initialiseBlackPieces() {
        ArrayList<Piece> black = new ArrayList<>();

        for (Piece boardPiece : boardPieces) {
            if (boardPiece.color == 'b') {
                black.add(boardPiece);
            }
        }
        return black;
    }

    public static void setBoard(String pattern) {
        for (int i = 0; i < pattern.length(); i++) {

            char c = pattern.charAt(i);
            int n = (int) c;

            switch (c) {
                case 'r' -> boardPieces.add(new Rook('b'));
                case 'n' -> boardPieces.add(new Knight('b'));
                case 'b' -> boardPieces.add(new Bishop('b'));
                case 'q' -> boardPieces.add(new Queen('b'));
                case 'k' -> boardPieces.add(new King('b'));
                case 'p' -> boardPieces.add(new Pawn('b'));

                case 'R' -> boardPieces.add(new Rook('w'));
                case 'N' -> boardPieces.add(new Knight('w'));
                case 'B' -> boardPieces.add(new Bishop('w'));
                case 'Q' -> boardPieces.add(new Queen('w'));
                case 'K' -> boardPieces.add(new King('w'));
                case 'P' -> boardPieces.add(new Pawn('w'));
                case '/' -> {} // Do nothing for '/'

                default -> {
                    // to handle numeric characters (ASCII 48-57)
                    if (Character.isDigit(c)) {

                        int emptySquareCount = c - '0';
                        for (int j = 0; j < emptySquareCount; j++) {
                            boardPieces.add(new EmptySquare());
                        }
                    }
                }
            }
        }

        for(int i=0; i<boardPieces.size();i++){
            Piece piece = boardPieces.get(i);
            int[] coordinates = findCoordinates(i);
            piece.setPosition(coordinates[0], coordinates[1]);
        }

    }

    public static void move(ArrayList<Piece> board, int indexPiece, int[] finalPosition) {
        Piece piece = board.get(indexPiece);
        int indexFinal = findPositionByLocation(finalPosition);
        EmptySquare emptySquare = (EmptySquare) board.get(indexFinal);
        Collections.swap(board, indexPiece, indexFinal);
        int[] initial = findCoordinates(indexPiece);

        emptySquare.setPosition(initial[0], initial[1]); //
        piece.setPosition(finalPosition[0], finalPosition[1]);

    }

    public static ArrayList<int[]> findAttacks(Piece piece){
        if (piece.name.equals("Pawn")) {
            Pawn pawn = (Pawn) piece;
            return null;
        }
        else{
            return findLegalMoves(piece);

        }
    }

    private static ArrayList<int[]> findLegalMoves(Piece piece) {
        ArrayList<int[]> legalMoves;

        switch (piece.name) {
            case "Pawn" -> {
                Pawn thisPawn = (Pawn) piece;
                legalMoves = thisPawn.legalMoves();
            }
            case "Knight" -> {
                Knight thisKnight = (Knight) piece;
                legalMoves = thisKnight.legalMoves();
            }
            case "Bishop" -> {
                Bishop thisBishop = (Bishop) piece;
                legalMoves = thisBishop.legalMoves();
            }
            case "Rook" -> {
                Rook thisRook = (Rook) piece;
                legalMoves= thisRook.legalMoves();
            }
            case "Queen" -> {
                Queen thisQueen = (Queen) piece;
                legalMoves = thisQueen.legalMoves();
            }
            case "King" -> {
                King thisKing = (King) piece;
                legalMoves = thisKing.legalMoves();
            }
            default -> {
                return null;
            }
        }

        return legalMoves;
    }


    public static int findPositionByLocation(int[] coords) {
        return (8 * coords[0]) + coords[1];
    }


    private static int[] findCoordinates(int location) {
        int row = (int) Math.floor(location / 8.0);
        int column = location % 8;
        return new int[] {row, column};
    }


}
