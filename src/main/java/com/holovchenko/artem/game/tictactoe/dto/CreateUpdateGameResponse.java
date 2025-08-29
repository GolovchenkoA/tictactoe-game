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
    private String player1;
    private String player2;
    private String currentPlayer;
    private GameStatus status;
    private Symbol[][] board;
}
