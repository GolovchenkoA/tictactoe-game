package com.holovchenko.artem.game.tictactoe.model;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<TicTacToeGame, String> {
}
