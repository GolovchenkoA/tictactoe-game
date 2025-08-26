package com.holovchenko.artem.game.tictactoe.db;

import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicTacToeGame {

    @Id
    private String id;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameStatus status;
    private Board board;

    public void setNextPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}
