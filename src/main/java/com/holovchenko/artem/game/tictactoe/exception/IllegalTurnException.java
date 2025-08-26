package com.holovchenko.artem.game.tictactoe.exception;

public class IllegalTurnException extends RuntimeException {

    public IllegalTurnException(String message) {
        super(message);
    }
}
