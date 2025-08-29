package com.holovchenko.artem.game.tictactoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovchenko.artem.game.tictactoe.controller.mapper.TicTacToeGameMapper;
import com.holovchenko.artem.game.tictactoe.controller.validator.CreateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.controller.validator.UpdateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateGameRequest;
import com.holovchenko.artem.game.tictactoe.helper.GameTemplate;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicTacToeGameMapper ticTacToeGameMapper;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private CreateGameRequestValidator createGameRequestValidator;

    @MockitoBean
    private UpdateGameRequestValidator updateGameRequestValidator;

    @Test
    void createGame_shouldReturnCreatedGame() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        TicTacToeGame game = GameTemplate.createGame();
        CreateGameRequest createGameRequest = new CreateGameRequest("Alice", "Bob");

        when(gameService.createGame(any(Player.class), any(Player.class))).thenReturn(game);
        // When & Then
        mockMvc.perform(post("/games")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createGameRequest)))
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