package com.holovchenko.artem.game.tictactoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovchenko.artem.game.tictactoe.controller.mapper.TicTacToeGameMapper;
import com.holovchenko.artem.game.tictactoe.controller.mapper.TicTacToeGameMapperImpl;
import com.holovchenko.artem.game.tictactoe.controller.validator.CreateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.controller.validator.UpdateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateGameRequest;
import com.holovchenko.artem.game.tictactoe.helper.GameRequestResponseTemplate;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
@Import(TicTacToeGameMapperImpl.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
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
        String player1 = "Alice";
        String player2 = "Bob";
        TicTacToeGame dbGameEntity = GameRequestResponseTemplate.createDbGameModel(player1, player2);

        when(gameService.createGame(any(Player.class), any(Player.class))).thenReturn(dbGameEntity);
        CreateGameRequest createGameRequest = new CreateGameRequest(dbGameEntity.getPlayer1().name(), dbGameEntity.getPlayer2().name());

        // When & Then
        mockMvc.perform(post("/games")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createGameRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(dbGameEntity.getId()))
                .andExpect(jsonPath("$.currentPlayer").value(player1))
//                .andExpect(jsonPath("$.currentPlayer.name").value(expectedResponse.getPlayer1()))
//                TODO
//                .andExpect(jsonPath("$.currentPlayer.symbol").value("X"))
                .andExpect(jsonPath("$.player1").value(player1))
//                .andExpect(jsonPath("$.player1.name").value(expectedResponse.getPlayer1()))
                .andExpect(jsonPath("$.player2").value(player2))
//                .andExpect(jsonPath("$.player2.name").value(expectedResponse.getPlayer2()))
//                .andExpect(jsonPath("$.player1.symbol").value("X"))
//                .andExpect(jsonPath("$.player2.symbol").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.board").exists());
    }

}