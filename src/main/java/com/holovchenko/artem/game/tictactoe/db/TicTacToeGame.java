package com.holovchenko.artem.game.tictactoe.db;

import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicTacToeGame {

    private String id;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameStatus status;
    private Board board;
}
