package test.chess;

import static org.junit.jupiter.api.Assertions.*;

import chess.Board;
import chess.Piece;
import chess.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
        int[] finalPosition = {2, 3};
        Piece pieceBeforeMove = boardState.get(indexPieceToMove);
        Board.move(boardState, indexPieceToMove, finalPosition);
        Piece pieceAfterMove = boardState.get(Board.findPositionByLocation(finalPosition));

        assertNull(boardState.get(Board.findPositionByLocation(pieceBeforeMove.getCurrentPosition())));
        assertEquals(pieceBeforeMove, pieceAfterMove);
    }

    @Test
    public void testFindAttacks() {
        Piece pawn = new Pawn(3, 3, 'w');
        ArrayList<int[]> attackMoves = Board.findAttacks(pawn);
        assertNotNull(attackMoves);
        assertEquals(2, attackMoves.size());

        // TODO do asserts on all types of pieces
    }

    @Test
    public void testCheckForCheckMate() {
        //TODO setup board for checkMate context
        boolean isCheckMate = Board.checkForCheckMate(boardState, 1);
        assertTrue(isCheckMate);
    }

    @Test
    public void testCheckForStalemate() {
        //TODO setup board for stalemate context
        boolean isStalemate = Board.checkForStalemate(boardState, 1); // Assuming it's white's turn
        assertTrue(isStalemate);
    }

    @Test
    public void testIsCheck() {
        //TODO setup board for check scenario
        boolean isChecked = Board.isCheck(boardState, 1); // Assuming it's white's turn
        assertTrue(isChecked);
    }

}
