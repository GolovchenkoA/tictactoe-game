package com.holovchenko.artem.game.tictactoe.controller.validator;

import com.holovchenko.artem.game.tictactoe.exception.InvalidGameRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UpdateGameRequestValidator {

    public void validate(String gameId, String player, int row, int column) {
        if (!StringUtils.hasText(gameId)) {
            throw new InvalidGameRequestException("Game ID must not be null or empty");
        }

        if (!StringUtils.hasText(player)) {
            throw new InvalidGameRequestException("Player must not be null or empty");
        }

        if (!isInBounds(row, column)) {
            String msg = String.format("The board game size is 3x3. But got %dx%d", row, column);
            throw new InvalidGameRequestException(msg);
        }
    }

    private boolean isInBounds(int row, int column) {
        return row >= 1 && row <= 3 && column >= 1 && column <= 3;
    }
}
