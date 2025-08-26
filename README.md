

## How to run the Application

```shell
gradlew build
docker-compose up --build -d
```

## How to stop the Application
```shell
docker-compose down -v
```

## How to play

You can import a Postman collection with all the methods below.
See `TicTacToe REST API.postman_collection` file in the root project folder

Creating a game.
```shell
POST localhost:8080/games?player1=player1&player2=player2
```

Get all games
```shell
GET localhost:8080/games
```

Player 1 move:
```shell
PATCH localhost:8080/games/{gameId}?player=player1&row=1&column=1
```

Player 2 move:
```shell
PATCH localhost:8080/games/{gameId}?player=player1&row=2&column=2
```


## Database
MongoDB is used as a storage. 
You can use [MongoDB Compass](https://www.mongodb.com/products/tools/compass) or [MongoDb Shell](https://www.mongodb.com/docs/mongodb-shell/install/) to connect to the DB.


uri: localhost:27017

login: root

password: root

db: tictactoe

collection: games

authSource: admin
