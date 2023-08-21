package chess;

import java.util.ArrayList;

public class Game {
    private boolean isGameOver = false;
    private ArrayList<int[]> moves = new ArrayList<>();

    public void start() {
        initializeGame();
//        ChessGUI gui = new ChessGUI();

        while (!isGameOver) {
//            boolean success = gameLoop(gui);
//            if (success) {
//                moves.add(new int[]{});
//            }
        }

        System.exit(0);
    }

    private void initializeGame() {
        isGameOver = false;
        Board.boardPieces = new ArrayList<>();
        Board.initializeBoard(ChessEngine.BOARD_STARTING_POSITION);
    }
}
