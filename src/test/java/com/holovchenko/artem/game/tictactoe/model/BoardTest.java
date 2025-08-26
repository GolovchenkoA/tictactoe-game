package com.holovchenko.artem.game.tictactoe.model;

import com.holovchenko.artem.game.tictactoe.exception.IllegalTurnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @BeforeEach
    public void beforeEach() {
        this.board = new Board();
    }

    @Test
    void constructorShouldCreateEmptyBoard() {
        Board board = new Board();

        for (Symbol[] symbol : board.getSymbols()) {
            for (Symbol value : symbol) {
                assertEquals(Symbol.EMPTY, value);
            }
        }
    }

    @Test
    void shouldPlaceSymbolWhenCellIsEmpty() {
        board.makeMove(0, 0, Symbol.X);
        assertEquals(Symbol.X, board.getSymbols()[0][0]);
    }

    @Test
    void shouldThrowExceptionWhenCellIsAlreadyOccupied() {
        board.makeMove(1, 1, Symbol.X);

        IllegalTurnException exception = assertThrows(
                IllegalTurnException.class,
                () -> board.makeMove(1, 1, Symbol.O)
        );

        assertTrue(exception.getMessage().contains("Symbol X already set"));
    }
}