package com.holovchenko.artem.game.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class SimpleTicTacToeStatusUpdaterTest {

    private SimpleTicTacToeStatusUpdater updater;
    private TicTacToeGame game;
    private Board board;

    @BeforeEach
    public void setUp() {
        updater = new SimpleTicTacToeStatusUpdater();
        game = mock(TicTacToeGame.class);
        board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
    }


    @Test
    public void testXWinsByRow() {
        Symbol[][] symbols = {
                {Symbol.X, Symbol.X, Symbol.X},
                {Symbol.O, Symbol.EMPTY, Symbol.EMPTY},
                {Symbol.EMPTY, Symbol.O, Symbol.EMPTY}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game).setStatus(GameStatus.X_WIN);
    }

    @Test
    public void testOWinsByColumn() {
        Symbol[][] symbols = {
                {Symbol.O, Symbol.X, Symbol.X},
                {Symbol.O, Symbol.EMPTY, Symbol.X},
                {Symbol.O, Symbol.EMPTY, Symbol.EMPTY}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game).setStatus(GameStatus.O_WIN);
    }

    @Test
    public void testXWinsByMainDiagonal() {
        Symbol[][] symbols = {
                {Symbol.X, Symbol.O, Symbol.EMPTY},
                {Symbol.O, Symbol.X, Symbol.EMPTY},
                {Symbol.EMPTY, Symbol.EMPTY, Symbol.X}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game).setStatus(GameStatus.X_WIN);
    }

    @Test
    public void testOWinsByAntiDiagonal() {
        Symbol[][] symbols = {
                {Symbol.X, Symbol.X, Symbol.O},
                {Symbol.X, Symbol.O, Symbol.EMPTY},
                {Symbol.O, Symbol.EMPTY, Symbol.EMPTY}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game).setStatus(GameStatus.O_WIN);
    }

    @Test
    public void testDrawNoOneWon() {
        Symbol[][] symbols = {
                {Symbol.X, Symbol.O, Symbol.X},
                {Symbol.O, Symbol.X, Symbol.O},
                {Symbol.O, Symbol.X, Symbol.O}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game).setStatus(GameStatus.NO_ONE_WON);
    }

    @Test
    public void testGameStillInProgress() {
        Symbol[][] symbols = {
                {Symbol.X, Symbol.O, Symbol.X},
                {Symbol.O, Symbol.EMPTY, Symbol.O},
                {Symbol.EMPTY, Symbol.X, Symbol.EMPTY}
        };
        mockBoard(symbols);

        updater.update(game);

        verify(game).setNextPlayer();
        verify(game, never()).setStatus(GameStatus.X_WIN);
        verify(game, never()).setStatus(GameStatus.O_WIN);
        verify(game, never()).setStatus(GameStatus.NO_ONE_WON);
    }

    private void mockBoard(Symbol[][] symbols) {
        when(board.getSymbols()).thenReturn(symbols);
    }
}