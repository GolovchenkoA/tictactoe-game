package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.exception.IllegalTurnException;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.HumanPlayer;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/games")
public class GameController {

    private final static Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @PostMapping
    public TicTacToeGame createGame(@RequestParam String player1, @RequestParam String player2) {

        var game = gameService.createGame(new HumanPlayer(player1, Symbol.X), new HumanPlayer(player2, Symbol.O));
        LOG.info("Game created (ID {} ). Player 1: {}", game.getId(), player2);

        return game;
    }

    @GetMapping
    public List<TicTacToeGame> getAllGames() {
        return gameService.getAllGames();
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<?> updateGame(@PathVariable String gameId,
                                        @RequestParam String player,
                                        @RequestParam int row,
                                        @RequestParam int column){

        if(!isInBounds(row, column)) {
            String msg = String.format("The board game size is 3x3. But got %dx%d", row, column);
            throw new IllegalTurnException(msg);
        }

        TicTacToeGame game = gameService.makeMove(gameId, player, row, column);

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            return ResponseEntity.ok("Game already finished " + game.getStatus());
        }

        return ResponseEntity.ok(game);
    }

    public boolean isInBounds(int row, int column) {
        return row >= 1 && row <= 3 && column >= 1 && column <= 3;
    }
}
