package com.holovchenko.artem.game.tictactoe.controller.mapper;

import com.holovchenko.artem.game.tictactoe.db.TicTacToeGame;
import com.holovchenko.artem.game.tictactoe.dto.CreateUpdateGameResponse;
import com.holovchenko.artem.game.tictactoe.model.Board;
import com.holovchenko.artem.game.tictactoe.model.Symbol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicTacToeGameMapper {

    TicTacToeGameMapper INSTANCE = Mappers.getMapper(TicTacToeGameMapper.class);

    default Symbol[][] map(Board board) {
        return board.getSymbols();
    }

    @Mapping(target = "gameId", source = "id")
    @Mapping(target = "player1", expression = "java(game.getPlayer1().name())")
    @Mapping(target = "player2", expression = "java(game.getPlayer2().name())")
    @Mapping(target = "currentPlayer", expression = "java(game.getCurrentPlayer().name())")
    @Mapping(target = "board", source = "board")
    CreateUpdateGameResponse toGameResponse(TicTacToeGame game);
}
