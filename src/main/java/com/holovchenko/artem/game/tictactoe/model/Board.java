package com.holovchenko.artem.game.tictactoe.model;

import java.util.Arrays;

import com.holovchenko.artem.game.tictactoe.exception.IllegalTurnException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    private Symbol[][] symbols = new Symbol[3][3];

    public Board() {
        for (Symbol[] symbol : symbols) {
            Arrays.fill(symbol, Symbol.EMPTY);
        }
    }

    public void makeMove(int row, int column, Symbol symbol) {
        if (symbols[row][column] == Symbol.EMPTY) {
            symbols[row][column] = symbol;
        } else {
            throw new IllegalTurnException(String.format("Symbol %s already set", symbols[row][column]));
        }
    }
}
