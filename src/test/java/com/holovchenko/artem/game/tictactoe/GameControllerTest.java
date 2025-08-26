package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Test
    void createGame_shouldReturnCreatedGame() throws Exception {
        // Given
        String gameId = UUID.randomUUID().toString();
        Player player1 = new HumanPlayer("player1", Symbol.X) ;
        Player player2 = new HumanPlayer("player2", Symbol.O) ;

        TicTacToeGame game = new TicTacToeGame();
        game.setId(gameId);
        game.setCurrentPlayer(player1);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setBoard(new Board());

        when(gameService.createGame(player1, player2)).thenReturn(game);
        // When & Then
        mockMvc.perform(post("/games")
                        .param("player1", player1.name())
                        .param("player2", player2.name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gameId))
                .andExpect(jsonPath("$.currentPlayer.name").value(player1.name()))
                .andExpect(jsonPath("$.currentPlayer.symbol").value("X"))
                .andExpect(jsonPath("$.player1.name").value(player1.name()))
                .andExpect(jsonPath("$.player2.name").value(player2.name()))
                .andExpect(jsonPath("$.player1.symbol").value("X"))
                .andExpect(jsonPath("$.player2.symbol").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.board").exists());
    }
}