package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameRepository;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public TicTacToeGame createGame(Player player1, Player player2) {
        TicTacToeGame game = TicTacToeGame.builder()
                .player1(player1)
                .player2(player2)
                .currentPlayer(player1)
                .status(GameStatus.IN_PROGRESS)
                .board(new Board())
                .build();

        return gameRepository.save(game);
    }

    public List<TicTacToeGame> getAllGames() {
        return gameRepository.findAll();
    }
}
