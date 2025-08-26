package com.holovchenko.artem.game.tictactoe.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void constructorShouldCreateEmptyBoard() {
        Board board = new Board();

        for (Symbol[] symbol : board.getSymbols()) {
            for (Symbol value : symbol) {
                assertEquals(Symbol.EMPTY, value);
            }
        }
    }
}