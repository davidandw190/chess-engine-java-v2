import java.util.ArrayList;

public class ChessApplication {

    public static final String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public static ArrayList<int[]> moves = new ArrayList<>();
    public static int buttonListener;
    public static int moveListener;
    public static boolean requestingMove;
    public static boolean requestingPiece;
    public static boolean requestingOption;
    public static int optionListener;

    public static boolean gameOver;


    public static void main(String[] args) throws InterruptedException {
        startGame();

    }

    private static void startGame() {
        gameOver = false;
    }
}