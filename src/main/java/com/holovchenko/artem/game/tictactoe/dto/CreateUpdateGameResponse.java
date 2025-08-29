package com.holovchenko.artem.game.tictactoe.dto;


import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateGameResponse {
    private String gameId;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameStatus status;
    private Symbol[][] board;
}
