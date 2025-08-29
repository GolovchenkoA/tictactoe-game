package com.holovchenko.artem.game.tictactoe.exception;

public class GameNotFoundException  extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }
}
