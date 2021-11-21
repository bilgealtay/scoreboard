# For creation docker image in local, you should make maven docker plugin skip false.
    `<skip>true</skip>`


# Above command will build the project and create a docker image.
    ./mvnw package

# Project based on Spring Boot and RestAPI. Project has a docker file and can be dockerized.

# Endpoints:
* `/api/v1/teams`; to create new Team
* `/api/v1/games`; to create new Game
* `/api/v1/games/score`; to update the score of the game
* `/api/v1/games/{id}`; to get Game

# Used `h2database` to store the data

# Logging and Exception Handling are added.