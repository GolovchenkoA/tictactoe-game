package com.holovchenko.artem.game.tictactoe;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.exception.IllegalTurnException;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.GameRepository;
import com.holovchenko.artem.game.tictactoe.model.GameStatus;
import com.holovchenko.artem.game.tictactoe.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SimpleTicTacToeStatusUpdater statusUpdater;

    public TicTacToeGame createGame(Player player1, Player player2) {
        TicTacToeGame game = TicTacToeGame.builder()
                .player1(player1)
                .player2(player2)
                .currentPlayer(player1)
                .status(GameStatus.IN_PROGRESS)
                .board(new Board())
                .build();

        return saveGame(game);
    }

    private TicTacToeGame saveGame(TicTacToeGame game) {
        return gameRepository.save(game);
    }

    public List<TicTacToeGame> getAllGames() {
        return gameRepository.findAll();
    }

    public TicTacToeGame makeMove(String gameId, String player, int row, int column) {
        TicTacToeGame game = getGame(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            return game;
        }

        validateUpdateRequest(game, player);

        game.getBoard().makeMove(--row, --column, game.getCurrentPlayer().symbol());
        statusUpdater.update(game);

        return gameRepository.save(game);
    }

    private void validateUpdateRequest(TicTacToeGame game, String player) {

        Player currentPlayer = game.getCurrentPlayer();
        if (!currentPlayer.name().equals(player)) {
            throw new IllegalTurnException("Not your turn");
        }
    }

    public Optional<TicTacToeGame> getGame(String id) {
        return gameRepository.findById(id);
    }
}
