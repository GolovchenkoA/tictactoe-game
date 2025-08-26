package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.model.GameRepository;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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


        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("/games?player1=%s&player2=%s", player1, player2),
                null,
                String.class
        );

//        ResponseEntity<TicTacToeGame> response = restTemplate.postForEntity(
//                String.format("/games?player1=%s&player2=%s", player1, player2),
//                null,
//                TicTacToeGame.class
//        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertNotNull(response.getBody().getId());
//        assertEquals(player1, response.getBody().getPlayer1().name());
//        assertEquals(player2, response.getBody().getPlayer2().name());
//        assertEquals(GameStatus.IN_PROGRESS, response.getBody().getStatus());
    }
}
