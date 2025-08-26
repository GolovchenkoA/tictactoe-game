package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.springframework.stereotype.Component;

@Component
public class SimpleTicTacToeStatusUpdater implements GameStatusUpdater {
    @Override
    public void update(TicTacToeGame game) {
        game.setNextPlayer();
        updateGameStatus(game);
    }

    private void updateGameStatus(TicTacToeGame game) {
        Symbol[][] symbols = game.getBoard().getSymbols();
        Symbol symbol = checkWinner(symbols);

        if (symbol == Symbol.X) {
            game.setStatus(GameStatus.X_WIN);
        } else if (symbol == Symbol.O) {
            game.setStatus(GameStatus.O_WIN);
        }else if (isBoardFull(symbols)) {
            game.setStatus(GameStatus.NO_ONE_WON);
        }
    }

    public boolean isBoardFull(Symbol[][] symbols) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (symbols[row][col] == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public Symbol checkWinner(Symbol[][] symbols) {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (symbols[row][0] != Symbol.EMPTY &&
                    symbols[row][0] == symbols[row][1] &&
                    symbols[row][1] == symbols[row][2]) {
                return symbols[row][0];
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (symbols[0][col] != Symbol.EMPTY &&
                    symbols[0][col] == symbols[1][col] &&
                    symbols[1][col] == symbols[2][col]) {
                return symbols[0][col];
            }
        }

        // Check main diagonal
        if (symbols[0][0] != Symbol.EMPTY &&
                symbols[0][0] == symbols[1][1] &&
                symbols[1][1] == symbols[2][2]) {
            return symbols[0][0];
        }

        // Check anti-diagonal
        if (symbols[0][2] != Symbol.EMPTY &&
                symbols[0][2] == symbols[1][1] &&
                symbols[1][1] == symbols[2][0]) {
            return symbols[0][2];
        }

        // No winner
        return Symbol.EMPTY;
    }
}
