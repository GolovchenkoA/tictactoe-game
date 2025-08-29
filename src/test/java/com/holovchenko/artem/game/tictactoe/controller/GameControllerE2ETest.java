package com.holovchenko.artem.game.tictactoe.controller;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateUpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.dto.UpdateGameRequest;
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

import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("player1", player1);
        requestBody.put("player2", player2);

        ResponseEntity<CreateUpdateGameResponse> response = restTemplate.postForEntity(
                "/games",
                requestBody,
                CreateUpdateGameResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getGameId());

        assertEquals(player1, response.getBody().getCurrentPlayer().name());
        assertEquals(player1, response.getBody().getPlayer1().name());
        assertEquals(player2, response.getBody().getPlayer2().name());

        assertEquals(Symbol.X, response.getBody().getCurrentPlayer().symbol());
        assertEquals(Symbol.X, response.getBody().getPlayer1().symbol());
        assertEquals(Symbol.O, response.getBody().getPlayer2().symbol());

        assertEquals(GameStatus.IN_PROGRESS, response.getBody().getStatus());
        assertNotNull(response.getBody().getBoard());

        for (Symbol[] symbol : response.getBody().getBoard()) {
            for (Symbol value : symbol) {
                assertEquals(Symbol.EMPTY, value);
            }
        }
    }


    @Test
    void shouldCreateGameAndPlayAndWin() {
        String player1Name = "player1";
        String player2Name = "player2";

        // You can pass null or an empty HttpEntity if body is not needed
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        Map<String, String> requestBody = Map.of("player1", player1Name, "player2", player2Name);

        // Use the requestBody in the postForEntity method
        ResponseEntity<CreateUpdateGameResponse> createResponse = restTemplate.postForEntity(
                "/games",
                requestBody,
                CreateUpdateGameResponse.class
        );

        String gameId = createResponse.getBody().getGameId();


        // Move X1
        UpdateGameRequest updateRequest = new UpdateGameRequest(player1Name, 1, 1);
        ResponseEntity<CreateUpdateGameResponse> response = restTemplate.exchange(
                String.format("/games/%s", gameId), 
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

// Move O1
        updateRequest = new UpdateGameRequest(player2Name, 2, 1);
        response = restTemplate.exchange(
                String.format("/games/%s", gameId), 
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

// Move X2
        updateRequest = new UpdateGameRequest(player1Name, 1, 2);
        response = restTemplate.exchange(
                String.format("/games/%s", gameId), 
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

// Move O2
        updateRequest = new UpdateGameRequest(player2Name, 2, 2);
        response = restTemplate.exchange(
                String.format("/games/%s", gameId), 
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GameStatus.IN_PROGRESS, response.getBody().getStatus());
        assertEquals(player1Name, response.getBody().getCurrentPlayer().name());
        assertEquals(Symbol.X, response.getBody().getCurrentPlayer().symbol());

// Move X3
        updateRequest = new UpdateGameRequest(player1Name, 1, 3);
        response = restTemplate.exchange(
                String.format("/games/%s", gameId), 
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GameStatus.X_WIN, response.getBody().getStatus());
    }

    @Test
    void shouldReturn409ErrorCodeWhen2MovesInRow() {
        String player1 = "player1";
        String player2 = "player2";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("player1", player1);
        requestBody.put("player2", player2);

        ResponseEntity<CreateUpdateGameResponse> createResponse = restTemplate.postForEntity(
                "/games",
                requestBody,
                CreateUpdateGameResponse.class
        );

        String gameId = createResponse.getBody().getGameId();

        // move X1
        UpdateGameRequest updateRequest = new UpdateGameRequest(player1, 1, 1);
        ResponseEntity<CreateUpdateGameResponse> response = restTemplate.exchange(
                String.format("/games/%s", gameId),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                CreateUpdateGameResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // move X2
        updateRequest = new UpdateGameRequest(player1, 1, 2);
        ResponseEntity<String> conflictResponse = restTemplate.exchange(
                String.format("/games/%s", gameId),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequest),
                String.class
        );
        assertEquals(HttpStatus.CONFLICT, conflictResponse.getStatusCode());

        String errorMsg = "Should return HTTP 409 code when a player tries to make a move 2 times in a row";
        assertEquals("Not your turn", conflictResponse.getBody(), errorMsg);
    }
}
