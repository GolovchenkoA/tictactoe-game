package com.holovchenko.artem.game.tictactoe.controller.validator;

import com.holovchenko.artem.game.tictactoe.exception.InvalidGameRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateGameRequestValidatorTest {

    private CreateGameRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CreateGameRequestValidator();
    }

    @Test
    void shouldPassValidation_WhenPlayersAreValid() {
        assertDoesNotThrow(() -> validator.validate("Alice", "Bob"));
    }

    @Test
    void shouldThrowException_WhenPlayer1IsNull() {
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(null, "Bob")
        );
        assertEquals("Player 1 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer2IsNull() {
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("Alice", null)
        );
        assertEquals("Player 2 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer1IsEmpty() {
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("   ", "Bob")
        );
        assertEquals("Player 1 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer2IsEmpty() {
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("Alice", "   ")
        );
        assertEquals("Player 2 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayerNamesAreSame_IgnoringCase() {
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate("Alice", "aLIce")
        );
        assertEquals("Player names must be different", exception.getMessage());
    }
}
