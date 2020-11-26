# Deploying customer microservice with Docker
## Prerequisites
Make sure you changed the directory to root folder of this project.

Then create a bridge network

    docker network create dvdrental
    
# Database
Build database docker image

    docker build -f src/main/docker/Dockerfile.db -t customer-db .
    
Now run two databases (one for testing and one for production)

    docker run --name customer_db -it --network dvdrental customer-db
    docker run --name customer_db_test -it --network dvdrental customer-db
    
# Quarkus Microservice
Create docker image to run native executable. During image build, the native executable will be build.

    docker build -f src/main/docker/Dockerfile.multistage -t customer-microservice --network dvdrental .
    
Now we can start the executable with

    docker run -i --rm -p 8080:8080 --name customer_microservice --network dvdrental customer-microservice
    
Test microservice for instance with following curl command

    curl -X GET "http://localhost:8080/customers/2" -H "accept: application/json"