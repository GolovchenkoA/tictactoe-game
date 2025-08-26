package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;

public interface GameStatusUpdater {
    void update(TicTacToeGame game);
}
