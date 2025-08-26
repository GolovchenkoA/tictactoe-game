package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public TicTacToeGame createGame(Player player1, Player player2) {
        TicTacToeGame game = TicTacToeGame.builder()
                .player1(player1)
                .player2(player2)
                .currentPlayer(player1)
                .status(GameStatus.IN_PROGRESS)
                .board(new Board())
                .build();
//        TODO
//        return gameRepository.save(game);
        return game;
    }
}
