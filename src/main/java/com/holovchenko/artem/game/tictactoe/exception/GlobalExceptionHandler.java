package com.holovchenko.artem.game.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "";

        if (message.toLowerCase().contains("not found")) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Not Found: " + message);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + message);
        }
    }

    @ExceptionHandler(IllegalTurnException.class)
    public ResponseEntity<String> handleIllegalTurns(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ex.getMessage());
    }
}
