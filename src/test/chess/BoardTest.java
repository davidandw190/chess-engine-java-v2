package test.chess;

import static org.junit.jupiter.api.Assertions.*;

import chess.Board;
import chess.Piece;
import chess.pieces.EmptySquare;
import chess.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class BoardTest {

    private static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    @BeforeEach
    public void setUp() {
        Board.initializeBoard(INITIAL_FEN);
        ArrayList<Piece> boardState = new ArrayList<>(Board.boardPieces);
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
    public void testCheckForCheckMate() {
        // Set up a board configuration where a checkmate occurs
        Board.initializeBoard("4k3/8/8/8/8/8/3q4/4K3");
        boolean isCheckmate = Board.checkForCheckMate(Board.boardPieces, 1); // White's turn

        assertTrue(isCheckmate);
    }

    @Test
    public void testCheckForStaleMate() {
        // Set up the stalemate scenario
        Board.initializeBoard("8/8/8/8/8/4k3/8/4K3");

        // Print the board configuration for debugging
        Board.printBoard(Board.boardPieces);

        boolean isStalemate = Board.checkForStalemate(Board.boardPieces, 1); // Black's turn

        assertTrue(isStalemate);
    }



    @Test
    public void testExcludeSelfChecks() {
        Board.initializeBoard("8/8/8/8/3q4/8/8/4K3");
        int indexPieceToMove = 60; // The white king

        ArrayList<int[]> legalMovesBefore = Board.findLegalMoves(Board.boardPieces.get(indexPieceToMove));
        ArrayList<int[]> legalMovesAfter = Board.excludeSelfChecks(legalMovesBefore, indexPieceToMove, 1);

        for (int[] move : legalMovesAfter) {
            ArrayList<Piece> simulatedBoard = new ArrayList<>(Board.boardPieces);
            boolean capture = Board.moveOrCapture(simulatedBoard, move, indexPieceToMove, Board.findPositionByLocation(move));
            assertFalse(Board.isCheck(simulatedBoard, 1));
        }
    }



    @Test
    public void testMove() {
        int indexPieceToMove = 8;
        int[] finalPosition = {2, 0};
        Piece pieceBeforeMove = Board.boardPieces.get(indexPieceToMove);
        Board.movePiece(Board.boardPieces, indexPieceToMove, finalPosition);
        Piece pieceAfterMove = Board.boardPieces.get(Board.findPositionByLocation(finalPosition));

//        assertEquals( finalPosition, pieceAfterMove.getCurrentPosition());
        assertEquals(pieceBeforeMove, pieceAfterMove);
        assert(Board.boardPieces.get(indexPieceToMove) instanceof EmptySquare);
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
