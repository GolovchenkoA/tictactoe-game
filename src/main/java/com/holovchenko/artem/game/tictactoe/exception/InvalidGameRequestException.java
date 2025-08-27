package com.holovchenko.artem.game.tictactoe.exception;

public class InvalidGameRequestException extends RuntimeException {
    public InvalidGameRequestException(String message) {
        super(message);
    }
}
