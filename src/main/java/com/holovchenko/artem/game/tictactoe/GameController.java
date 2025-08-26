package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/games")
public class GameController {

    private final static Logger LOG = LoggerFactory.getLogger(GameController.class);

    @PostMapping
    public TicTacToeGame createGame(@RequestParam String player1, @RequestParam String player2) {
        Player currentPlayer = new HumanPlayer(player1, Symbol.X);
        TicTacToeGame game = TicTacToeGame.builder()
                .player1(currentPlayer)
                .player2(new HumanPlayer(player2, Symbol.O))
                .currentPlayer(currentPlayer)
                .status(GameStatus.IN_PROGRESS)
                .board(new Board())
                .build();

        LOG.info("Game created (ID {} ). Player 1: {}", game.getId(), player2);

        return game;
    }
}
