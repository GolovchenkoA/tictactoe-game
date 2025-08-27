package com.holovchenko.artem.game.tictactoe.controller.validator;

import com.holovchenko.artem.game.tictactoe.exception.InvalidGameRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateGameRequestValidatorTest {

    private UpdateGameRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UpdateGameRequestValidator();
    }

    @Test
    void shouldPassValidation_WhenAllInputsAreValid() {
        assertDoesNotThrow(() -> validator.validate("game123", "Alice", 2, 3));
    }

    @Test
    void shouldThrowException_WhenGameIdIsNull() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(null, "Alice", 2, 2)
        );
        assertEquals("Game ID must not be null or empty", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenGameIdIsBlank() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("   ", "Alice", 2, 2)
        );
        assertEquals("Game ID must not be null or empty", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayerIsNull() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", null, 2, 2)
        );
        assertEquals("Player must not be null or empty", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayerIsBlank() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", "   ", 2, 2)
        );
        assertEquals("Player must not be null or empty", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenRowIsOutOfBoundsLow() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", "Alice", 0, 2)
        );
        assertEquals("The board game size is 3x3. But got 0x2", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenRowIsOutOfBoundsHigh() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", "Alice", 4, 1)
        );
        assertEquals("The board game size is 3x3. But got 4x1", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenColumnIsOutOfBoundsLow() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", "Alice", 1, 0)
        );
        assertEquals("The board game size is 3x3. But got 1x0", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenColumnIsOutOfBoundsHigh() {
        InvalidGameRequestException ex = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("game123", "Alice", 2, 5)
        );
        assertEquals("The board game size is 3x3. But got 2x5", ex.getMessage());
    }
}
