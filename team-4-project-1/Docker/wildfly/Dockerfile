FROM jboss/wildfly
USER root
RUN mkdir -p /opt/jboss/wildfly/modules/org/postgresql/main \
    && cd /opt/jboss/wildfly/modules/org/postgresql/main \
    && curl -O "https://jdbc.postgresql.org/download/postgresql-42.2.11.jar"
COPY module.xml /opt/jboss/wildfly/modules/org/postgresql/main
USER jboss
EXPOSE 8080
EXPOSE 9990
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

