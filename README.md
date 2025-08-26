```shell
gradlew build
#docker build -t tiktaktoe:0.0.1-SNAPSHOT .
docker-compose up --build -d
```

```shell
docker-compose down
```

## How to play

You can import a Postman collection with all the methods below.
See "TicTacToe REST API.postman_collection" file in the root project folder

Creating a game
```shell
POST localhost:8080/games?player1=player1&player2=player2
```

Get all games
```shell
GET localhost:8080/games
```

## Database
MongoDB is used as a storage.
uri: localhost:27017
login: root
password: root
db: tictactoe
collection: games
authSource: admin
