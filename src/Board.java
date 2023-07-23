
import pieces.*;

import java.util.ArrayList;

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

            // add the appropriate piece type in terms of c in pattern
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
            }
        }
    }



}
