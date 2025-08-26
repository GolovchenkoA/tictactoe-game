
db = db.getSiblingDB('tictactoe');

db.createCollection('games');

//db.games.insertOne({
//  player1: {
//    name: "player1",
//    symbol: "X",
//    _class: "com.holovchenko.artem.game.tictactoe.model.HumanPlayer"
//  },
//  player2: {
//    name: "player2",
//    symbol: "Y",
//    _class: "com.holovchenko.artem.game.tictactoe.model.HumanPlayer"
//  },
//  currentPlayer: {
//    name: "player1",
//    symbol: "X",
//    _class: "com.holovchenko.artem.game.tictactoe.model.HumanPlayer"
//  },
//  status: "IN_PROGRESS",
//  board: {
//    symbols: [
//      ["EMPTY", "EMPTY", "EMPTY"],
//      ["EMPTY", "EMPTY", "EMPTY"],
//      ["EMPTY", "EMPTY", "EMPTY"]
//    ]
//  },
//  _class: "com.holovchenko.artem.game.tictactoe.db.TicTacToeGame"
//});