package test.chess;

import static org.junit.jupiter.api.Assertions.*;

import chess.Board;
import chess.Piece;
import chess.pieces.EmptySquare;
import chess.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardTest {

    private static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    private ArrayList<Piece> boardState;

    @BeforeEach
    public void setUp() {
        boardState = new ArrayList<>();
        Board.initializeBoard(INITIAL_FEN);
        boardState.addAll(Board.boardPieces);
    }

    @Test
    public void testGetWhitePieces() {
        ArrayList<Piece> whitePieces = Board.getWhitePieces();
        assertNotNull(whitePieces);

        assertEquals(16, whitePieces.size());

        for (Piece whitePiece : whitePieces) {
            assertEquals('w', whitePiece.getColor());
        }
    }

    @Test
    public void testGetBlackPieces() {
        ArrayList<Piece> blackPieces = Board.getBlackPieces();
        assertNotNull(blackPieces);
        assertEquals(16, blackPieces.size());

        for (Piece blackPiece : blackPieces) {
            assertEquals('b', blackPiece.getColor());
        }
    }

    @Test
    public void testMove() {
        int indexPieceToMove = 8;
        int[] finalPosition = {2, 0};
        Piece pieceBeforeMove = Board.boardPieces.get(indexPieceToMove);
        Board.move(Board.boardPieces, indexPieceToMove, finalPosition);
        Piece pieceAfterMove = Board.boardPieces.get(Board.findPositionByLocation(finalPosition));

//        assertEquals( finalPosition, pieceAfterMove.getCurrentPosition());
        assertEquals(pieceBeforeMove, pieceAfterMove);
        assert(Board.boardPieces.get(indexPieceToMove) instanceof EmptySquare);
    }

    @Test
    public void testCheckmate() {
        // Initialize the board with a checkmate scenario
        String fen = "8/8/8/8/4k3/8/8/4K3"; // Black king in checkmate
        Board.initializeBoard(fen);

        boolean isCheckmate = Board.checkForCheckMate(Board.boardPieces, 2);

        assertTrue(isCheckmate, "Checkmate should be detected.");
    }

    @Test
    public void testFindAttacks() {
        for (Piece piece : Board.getBlackPieces()) {
            if (piece instanceof Pawn) {
                ArrayList<int[]> attackMoves = Board.findAttacks(piece);
                assertNotNull(attackMoves);
                assertEquals(2, attackMoves.size());
            }
        }

        // TODO do asserts on all types of pieces
    }

    @Test
    public void testIsCheck() {
        Board.initializeBoard("8/8/8/4q3/8/8/8/4K3");
        boolean isChecked = Board.isCheck(Board.boardPieces, 1); // White's turn
        assertTrue(isChecked);
    }

}
