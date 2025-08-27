package com.holovchenko.artem.game.tictactoe.controller;

import com.holovchenko.artem.game.tictactoe.controller.validator.CreateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.controller.validator.UpdateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.helper.GameTemplate;
import com.holovchenko.artem.game.tictactoe.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockitoBean
    private CreateGameRequestValidator createGameRequestValidator;

    @MockitoBean
    private UpdateGameRequestValidator updateGameRequestValidator;

    @Test
    void createGame_shouldReturnCreatedGame() throws Exception {
        // Given
        TicTacToeGame game = GameTemplate.createGame();

        when(gameService.createGame(game.getPlayer1(), game.getPlayer2())).thenReturn(game);
        // When & Then
        mockMvc.perform(post("/games")
                        .param("player1", game.getPlayer1().name())
                        .param("player2", game.getPlayer2().name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(game.getId()))
                .andExpect(jsonPath("$.currentPlayer.name").value(game.getPlayer1().name()))
                .andExpect(jsonPath("$.currentPlayer.symbol").value("X"))
                .andExpect(jsonPath("$.player1.name").value(game.getPlayer1().name()))
                .andExpect(jsonPath("$.player2.name").value(game.getPlayer2().name()))
                .andExpect(jsonPath("$.player1.symbol").value("X"))
                .andExpect(jsonPath("$.player2.symbol").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.board").exists());
    }

}