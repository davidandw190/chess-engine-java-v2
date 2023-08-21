package chess;

import java.util.ArrayList;

public class ChessEngine {
    public static final String BOARD_STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public static ArrayList<int[]> moves = new ArrayList<>();
    public static boolean isGameOver;



    public static void main(String[] args) {

        var game = new Game();
        game.start();
    }
}
