#!/bin/bash

docker network create dvdrental

#build the database containers
docker build -f dvdrental-customer/src/main/docker/Dockerfile.db -t customer-db ./dvdrental-customer
docker build -f dvdrental-film/src/main/docker/Dockerfile.db -t film-db ./dvdrental-film
docker build -f dvdrental-store/src/main/docker/Dockerfile.db -t store-db ./dvdrental-store

#run database containers, so that the microservices can be built (running db needed for tests)
docker run --rm --name customer_db -d --network dvdrental customer-db
docker run --rm --name customer_db_test -d --network dvdrental customer-db
docker run --rm --name film_db -d --network dvdrental film-db
docker run --rm --name film_db_test -d --network dvdrental film-db
docker run --rm --name store_db -d --network dvdrental store-db
docker run --rm --name store_db_test -d --network dvdrental store-db

#build microservice containers after database containers are up
#Note: The Dockerfiles are built from the subdirectories root as context, otherwise the Dockerfiles can't access the ./src directory of the project directory
docker build -f dvdrental-customer/src/main/docker/Dockerfile.multistage --network dvdrental -t customer-microservice ./dvdrental-customer
docker build -f dvdrental-film/src/main/docker/Dockerfile.multistage --network dvdrental -t film-microservice ./dvdrental-film
docker build -f dvdrental-store/src/main/docker/Dockerfile.multistage --network dvdrental -t store-microservice ./dvdrental-store
docker build -f dvdrental-ui/src/main/docker/wildfly/Dockerfile --network dvdrental -t ui-microservice ./dvdrental-ui

# Stop test containers
docker stop customer_db_test
docker stop film_db_test
docker stop store_db_test

#run microservice containers
docker run --rm --name customer_microservice -d --network dvdrental customer-microservice
docker run --rm --name film_microservice -d --network dvdrental film-microservice
docker run --rm --name store_microservice -d --network dvdrental store-microservice

#run the wildfly container
docker run --rm -p 8080:8080 -p 9990:9990 --name ui_microservice -d --network dvdrental ui-microservice
