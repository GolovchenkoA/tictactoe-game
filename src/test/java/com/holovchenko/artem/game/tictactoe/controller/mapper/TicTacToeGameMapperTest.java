package com.holovchenko.artem.game.tictactoe.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.UpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TicTacToeGameMapperTest {

    private final TicTacToeGameMapper gameMapper = Mappers.getMapper(TicTacToeGameMapper.class);

    @Test
    void testMapToUpdateGameResponse() {
        //Given
        TicTacToeGame game = new TicTacToeGame();
        game.setId("123");
        game.setPlayer1(new Player("Alice", Symbol.X));
        game.setPlayer2(new Player("Bob", Symbol.O));
        game.setCurrentPlayer(new Player("Alice", Symbol.X));
        game.setStatus(GameStatus.IN_PROGRESS);

        Board board = new Board();
        board.getSymbols()[0][0] = Symbol.X;
        game.setBoard(board);

        //When
        UpdateGameResponse response = gameMapper.toUpdateGameResponse(game);

        //Then
        assertEquals("123", response.getGameId());
        assertEquals("Alice", response.getPlayer1());
        assertEquals("Bob", response.getPlayer2());
        assertEquals("Alice", response.getCurrentPlayer());
        assertEquals(GameStatus.IN_PROGRESS, response.getStatus());
        assertNotNull(response.getBoard());
        assertEquals(Symbol.X, response.getBoard()[0][0]);
    }
}