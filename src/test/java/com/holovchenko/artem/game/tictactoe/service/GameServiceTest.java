package com.holovchenko.artem.game.tictactoe.service;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.exception.IllegalTurnException;
import com.holovchenko.artem.game.tictactoe.helper.GameRequestResponseTemplate;
import com.holovchenko.artem.game.tictactoe.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SimpleStatusUpdater statusUpdater;

    @InjectMocks
    private GameService gameService;

    @Test
    void shouldSaveGameWhenGameCreated() {
        Player player1 = new Player("player1", Symbol.X) ;
        Player player2 = new Player("player2", Symbol.O) ;
        gameService.createGame(player1, player2);

        verify(gameRepository, times(1)).save(any());
    }

    @Test
    void shouldNotUpdateDbEntityWhenGameIsFinished() {
        //Given

        String gameID = "UUID-ID-1";

        TicTacToeGame game = GameRequestResponseTemplate.createDbGameModel();
        game.setId(gameID);
        game.setStatus(GameStatus.NO_ONE_WON);
        when(gameRepository.findById(gameID)).thenReturn(Optional.of(game));

        // When
        int row = 1;
        int column = 1;
        TicTacToeGame updatedGame = gameService.makeMove(gameID, "player1", row, column);

        // Then
        assertEquals(GameStatus.NO_ONE_WON, game.getStatus());
        assertEquals(game, updatedGame);
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    void shouldUpdateAndSaveGame() {
        //Given

        String gameID = "UUID-ID-1";

        TicTacToeGame game = GameRequestResponseTemplate.createDbGameModel();
        game.setId(gameID);
        when(gameRepository.findById(gameID)).thenReturn(Optional.of(game));

        // When
        int row = 1;
        int column = 1;
        gameService.makeMove(gameID, "player1", row, column);

        // Then
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        verify(statusUpdater, times(1)).update(any());
        verify(gameRepository, times(1)).save(any());
    }


    @Test
    void shouldThrowExceptionWhenNotUserMove() {
        //Given
        String gameID = "UUID-ID-1";
        TicTacToeGame game = GameRequestResponseTemplate.createDbGameModel();
        game.setId(gameID);
        game.setCurrentPlayer(game.getPlayer2());

        when(gameRepository.findById(gameID)).thenReturn(Optional.of(game));

        // When-Then
        int row = 1;
        int column = 1;
        assertThrows(
                IllegalTurnException.class, // or the specific exception you expect
                () -> gameService.makeMove(gameID, game.getPlayer1().name(), row, column)
        );
    }
}