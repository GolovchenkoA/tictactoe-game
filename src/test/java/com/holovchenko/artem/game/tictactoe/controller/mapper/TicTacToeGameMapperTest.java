package com.holovchenko.artem.game.tictactoe.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateUpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TicTacToeGameMapperTest {

    private final TicTacToeGameMapper gameMapper = Mappers.getMapper(TicTacToeGameMapper.class);

    @Test
    void testMaptoGameResponse() {
        //Given
        TicTacToeGame game = new TicTacToeGame();
        Player player1 = new Player("Alice", Symbol.X);
        Player player2 = new Player("Bob", Symbol.O);
        game.setId("123");
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setCurrentPlayer(player1);
        game.setStatus(GameStatus.IN_PROGRESS);

        Board board = new Board();
        board.getSymbols()[0][0] = Symbol.X;
        game.setBoard(board);

        //When
        CreateUpdateGameResponse response = gameMapper.toGameResponse(game);

        //Then
        assertEquals("123", response.getGameId());
        assertEquals("Alice", response.getPlayer1().name());
        assertEquals(Symbol.X, response.getPlayer1().symbol());
        assertEquals("Bob", response.getPlayer2().name());
        assertEquals(Symbol.O, response.getPlayer2().symbol());

        assertEquals(player1, response.getCurrentPlayer());

        assertEquals(GameStatus.IN_PROGRESS, response.getStatus());
        assertNotNull(response.getBoard());
        assertEquals(Symbol.X, response.getBoard()[0][0]);
    }
}