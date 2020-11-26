# Deploying store microservice with Docker
## Prerequisites
Make sure you changed the directory to root folder of this project.

Then create a bridge network

    docker network create dvdrental
    
# Database
Build the image

    docker build -f src/main/docker/Dockerfile.db -t store-db .
    
Now run two databases (one for testing and one for production)

    docker run --name store_db -it --network dvdrental store-db
    docker run --name store_db_test -it --network dvdrental store-db
    
# Quarkus Microservice
Create docker image to run native executable. During image build, the native executable will be build.

    docker build -f src/main/docker/Dockerfile.multistage -t store-microservice --network dvdrental .
    
Now we can start the executable with

    docker run -i --rm -p 8080:8080 --name store_microservice --network dvdrental store-microservice
    
Test microservice for instance with following curl command

    curl -X GET "http://localhost:8080/stores/2" -H "accept: application/json"