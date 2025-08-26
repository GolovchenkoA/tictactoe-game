package com.holovchenko.artem.game.tictactoe.model;

import java.util.Arrays;

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
}
