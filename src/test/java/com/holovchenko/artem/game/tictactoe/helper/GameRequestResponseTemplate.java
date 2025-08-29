package com.holovchenko.artem.game.tictactoe.helper;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateUpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.model.*;

import java.util.UUID;

public class GameRequestResponseTemplate {

    public static TicTacToeGame createDbGameModel() {
        return createDbGameModel("player1", "player2");
    }

    public static TicTacToeGame createDbGameModel(String player1Name, String player2Name) {
        TicTacToeGame game = new TicTacToeGame();

        String gameId = UUID.randomUUID().toString();
        Player player1 = new Player(player1Name, Symbol.X) ;
        Player player2 = new Player(player2Name, Symbol.O) ;

        game.setId(gameId);
        game.setCurrentPlayer(player1);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setBoard(new Board());

        return game;
    }

    public static CreateUpdateGameResponse createResponse() {
        CreateUpdateGameResponse game = new CreateUpdateGameResponse();

        String gameId = UUID.randomUUID().toString();
        Player player1 = new Player("player1", Symbol.X) ;
        Player player2 = new Player("player2", Symbol.O) ;

        game.setGameId(gameId);
        game.setCurrentPlayer(player1);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setBoard(new Board().getSymbols());

        return game;
    }
}
