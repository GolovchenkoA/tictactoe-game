package com.holovchenko.artem.game.tictactoe.controller;

import com.holovchenko.artem.game.tictactoe.controller.mapper.TicTacToeGameMapper;
import com.holovchenko.artem.game.tictactoe.dto.CreateGameRequest;
import com.holovchenko.artem.game.tictactoe.dto.UpdateGameRequest;
import com.holovchenko.artem.game.tictactoe.dto.CreateUpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.service.GameService;
import com.holovchenko.artem.game.tictactoe.controller.validator.CreateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.controller.validator.UpdateGameRequestValidator;
import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/games")
@Validated
public class GameController {

    private final static Logger LOG = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private final TicTacToeGameMapper gameMapper;
    private final CreateGameRequestValidator createGameRequestValidator;
    private final UpdateGameRequestValidator updateGameValidator;

    public GameController(GameService gameService, TicTacToeGameMapper gameMapper, CreateGameRequestValidator createGameRequestValidator, UpdateGameRequestValidator updateGameValidator) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.createGameRequestValidator = createGameRequestValidator;
        this.updateGameValidator = updateGameValidator;
    }

    @PostMapping
    public CreateUpdateGameResponse createGame(@RequestBody CreateGameRequest requestBody) {

        createGameRequestValidator.validate(requestBody);

        String player1 = requestBody.getPlayer1();
        String player2 = requestBody.getPlayer2();

        var game = gameService.createGame(new Player(player1, Symbol.X), new Player(player2, Symbol.O));
        LOG.info("Game created (ID {} ). Player 1: {} Player 2: {}", game.getId(), player1, player2);

        return gameMapper.toGameResponse(game);
    }

    @GetMapping
    public List<TicTacToeGame> getAllGames() {
        return gameService.getAllGames();
    }

    @PatchMapping("/{gameId}")
    public CreateUpdateGameResponse updateGame(@PathVariable String gameId, @RequestBody UpdateGameRequest requestBody) {

        updateGameValidator.validate(gameId,
                requestBody.getPlayer(),
                requestBody.getRow(),
                requestBody.getColumn());

        TicTacToeGame game = gameService.makeMove(gameId,
                requestBody.getPlayer(),
                requestBody.getRow(),
                requestBody.getColumn());

        return gameMapper.toGameResponse(game);
    }
}
