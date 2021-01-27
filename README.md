# Team-Project
Team Project from the class. Separated into 3 Projects.
First project is a monolithic java ee app (on an application server), where the db is used via jndi and the frontend is serverside rendered via jsf.
The second project is the first project but now coupled into an RESTAPI (documented via OpenAPI and SwaggerUI) which has 3 endpoints, and the frontend which retrieves the data via ajax.
The third project separates the backend and the frontend into multiple microservices with Quarkus, which are started via Docker.
