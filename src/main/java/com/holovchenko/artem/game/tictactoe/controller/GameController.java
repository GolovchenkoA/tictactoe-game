package com.holovchenko.artem.game.tictactoe.controller;

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
    private final CreateGameRequestValidator createGameRequestValidator;
    private final UpdateGameRequestValidator updateGameValidator;

    public GameController(GameService gameService, CreateGameRequestValidator createGameRequestValidator, UpdateGameRequestValidator updateGameValidator) {
        this.gameService = gameService;
        this.createGameRequestValidator = createGameRequestValidator;
        this.updateGameValidator = updateGameValidator;
    }

    @PostMapping
    public TicTacToeGame createGame(@RequestParam String player1, @RequestParam String player2) {

        createGameRequestValidator.validate(player1, player2);

        var game = gameService.createGame(new Player(player1, Symbol.X), new Player(player2, Symbol.O));
        LOG.info("Game created (ID {} ). Player 1: {}", game.getId(), player2);

        return game;
    }

    @GetMapping
    public List<TicTacToeGame> getAllGames() {
        return gameService.getAllGames();
    }

    @PatchMapping("/{gameId}")
    public TicTacToeGame updateGame(@PathVariable String gameId,
                                        @RequestParam String player,
                                        @RequestParam int row,
                                        @RequestParam int column){

        updateGameValidator.validate(gameId, player, row, column);

//        TODO: create TicTacToeGameDTO
        return gameService.makeMove(gameId, player, row, column);
    }
}
