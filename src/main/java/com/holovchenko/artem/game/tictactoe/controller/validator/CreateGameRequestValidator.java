package com.holovchenko.artem.game.tictactoe.controller.validator;

import com.holovchenko.artem.game.tictactoe.exception.InvalidGameRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CreateGameRequestValidator {

    public void validate(String player1, String player2) {
        if (!StringUtils.hasText(player1)) {
            throw new InvalidGameRequestException("Player 1 must not be null or empty");
        }

        if (!StringUtils.hasText(player2)) {
            throw new InvalidGameRequestException("Player 2 must not be null or empty");
        }

        if (player1.equalsIgnoreCase(player2)) {
            throw new InvalidGameRequestException("Player names must be different");
        }
    }
}
