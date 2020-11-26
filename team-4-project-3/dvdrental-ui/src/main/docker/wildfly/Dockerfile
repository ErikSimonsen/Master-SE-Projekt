FROM maven:3.6-jdk-11 AS build
COPY src /usr/app/src
COPY pom.xml /usr/app
USER root
RUN mvn -f /usr/app/pom.xml -P clean package

FROM jboss/wildfly:19.0.0.Final
COPY --from=build /usr/app/target/*.war /opt/jboss/wildfly/standalone/deployments/

USER jboss
EXPOSE 8080
EXPOSE 9990
ENV FILM_MICROSERVICE_ADDRESS "http://film_microservice:8080"
ENV CUSTOMER_MICROSERVICE_ADDRESS "http://customer_microservice:8080"
ENV STORE_MICROSERVICE_ADDRESS "http://store_microservice:8080"
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]