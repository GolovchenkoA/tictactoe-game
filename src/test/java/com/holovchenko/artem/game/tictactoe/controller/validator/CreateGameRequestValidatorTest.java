package com.holovchenko.artem.game.tictactoe.controller.validator;

import com.holovchenko.artem.game.tictactoe.dto.CreateGameRequest;
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
        CreateGameRequest request = new CreateGameRequest("Alice", "Bob");
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void shouldThrowException_WhenPlayer1IsNull() {
        CreateGameRequest request = new CreateGameRequest(null, "Bob");
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(request)
        );
        assertEquals("Player 1 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer2IsNull() {
        CreateGameRequest request = new CreateGameRequest("Alice", null);
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(request)
        );
        assertEquals("Player 2 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer1IsEmpty() {
        CreateGameRequest request = new CreateGameRequest("   ", "Bob");
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(request)
        );
        assertEquals("Player 1 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayer2IsEmpty() {
        CreateGameRequest request = new CreateGameRequest("Alice", "   ");
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(request)
        );
        assertEquals("Player 2 must not be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenPlayerNamesAreSame_IgnoringCase() {
        CreateGameRequest request = new CreateGameRequest("Alice", "aLIce");
        InvalidGameRequestException exception = assertThrows(
                InvalidGameRequestException.class,
                () -> validator.validate(request)
        );
        assertEquals("Player names must be different", exception.getMessage());
    }
}
