package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.GameRepository;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//TODO: use a dedicated folder for e2e.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class GameControllerE2ETest {

    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7")
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongodb/mongodb-community-server:7.0-ubuntu2204")
            .withReuse(true);

    @DynamicPropertySource
    static void configureMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void cleanDb() {
        gameRepository.deleteAll();
    }

    @Test
    void shouldCreateGame() {
        String player1 = "player1";
        String player2 = "player2";

        ResponseEntity<TicTacToeGame> response = restTemplate.postForEntity(
                String.format("/games?player1=%s&player2=%s", player1, player2),
                null,
                TicTacToeGame.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(player1, response.getBody().getPlayer1().name());
        assertEquals(player2, response.getBody().getPlayer2().name());
        assertEquals(player1, response.getBody().getCurrentPlayer().name());
        assertEquals(GameStatus.IN_PROGRESS, response.getBody().getStatus());
        assertNotNull(response.getBody().getBoard());

        for (Symbol[] symbol : response.getBody().getBoard().getSymbols()) {
            for (Symbol value : symbol) {
                assertEquals(Symbol.EMPTY, value);
            }
        }
    }


    @Test
    void shouldCreateGameAndPlay() {
        String player1 = "player1";
        String player2 = "player2";

        // You can pass null or an empty HttpEntity if body is not needed
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<TicTacToeGame> response = restTemplate.postForEntity(
                String.format("/games?player1=%s&player2=%s", player1, player2),
                null,
                TicTacToeGame.class
        );

        String gameId = response.getBody().getId();

        // move X1
        String url = String.format("/games/%s?player=%s&row=%d&column=%d", gameId, player1, 1, 1);
        response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, TicTacToeGame.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // move O1
        url = String.format("/games/%s?player=%s&row=%d&column=%d", gameId, player2, 2, 1);
        response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, TicTacToeGame.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // move X2
        url = String.format("/games/%s?player=%s&row=%d&column=%d", gameId, player1, 1, 2);
        response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, TicTacToeGame.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // move O2
        url = String.format("/games/%s?player=%s&row=%d&column=%d", gameId, player2, 2, 2);
        response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, TicTacToeGame.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GameStatus.IN_PROGRESS, response.getBody().getStatus());
        assertEquals(player1, response.getBody().getCurrentPlayer().name());

        // move X3
        url = String.format("/games/%s?player=%s&row=%d&column=%d", gameId, player1, 1, 3);
        response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, TicTacToeGame.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GameStatus.X_WON, response.getBody().getStatus());
    }
}
