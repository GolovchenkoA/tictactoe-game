package com.holovchenko.artem.game.tictactoe.helper;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.*;

import java.util.UUID;

public class GameTemplate {

    public static TicTacToeGame createGame() {
        TicTacToeGame game = new TicTacToeGame();

        String gameId = UUID.randomUUID().toString();
        Player player1 = new HumanPlayer("player1", Symbol.X) ;
        Player player2 = new HumanPlayer("player2", Symbol.O) ;

        game.setId(gameId);
        game.setCurrentPlayer(player1);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setBoard(new Board());

        return game;
    }
}
