# Starting Wildfly and production Database 
    sudo docker-compose build
    sudo docker-compose up
This will start the wildfly server and the production database. Wildfly is configured with two datasources: The production datasource and the testing datasource.

# Build project (with executing test) and deploy war
First we have to build the database image, so we can use it in docker run.

    cd postgres
    sudo docker build --tag dvdrental_db .
Now we can run the test database:

    sudo docker run -d --name db_test --network dvdrental -p 5433:5432 dvdrental_db
    
The database is available at port 5433.
 
Now you can build the project and execute the tests with (don't forget to change directory to project root folder):

    mvn clean package
    
To deploy the created war you have to copy it to the deployment folder of the wildfly:

    cp target/dvdrental.war Docker/wildfly/deployments/
    
The database is changed now, to reset it just stop and remove the old testing database and start it again:

    sudo docker stop db_test
    sudo docker rm db_test
    sudo docker run -d --name db_test --network dvdrental -p 5433:5432 dvdrental_db
   
# Shortcut

A good shortcut to start the test database and build/deploy the project is this:

    sudo docker run -d --name db_test --network dvdrental -p 5433:5432 --rm dvdrental_db; mvn clean package; cp target/dvdrental.war Docker/wildfly/deployments/; sudo docker stop db_test
   
For Docker on Windows (works with Powershell and Windows Terminal):

    docker run -d --name db_test --network dvdrental -p 5433:5432 --rm dvdrental_db; mvn clean package; cp target/dvdrental.war Docker/wildfly/deployments/; docker stop db_test
 
