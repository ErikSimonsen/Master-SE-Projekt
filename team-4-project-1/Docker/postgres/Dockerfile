FROM postgres:latest
ENV POSTGRES_PASSWORD mysecretpassword
COPY init-user-db.sh /docker-entrypoint-initdb.d
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates wget unzip && rm -rf /var/lib/apt/lists/* \
    && cd /docker-entrypoint-initdb.d \
    && wget "https://sp.postgresqltutorial.com/wp-content/uploads/2019/05/dvdrental.zip" \
    && unzip dvdrental.zip \
    && tar xf dvdrental.tar \
    && rm dvdrental.zip dvdrental.tar \
    && sed -i 's/English_United States.1252/en_US.utf8/g' restore.sql \
    && sed -i 's/\$\$PATH\$\$/\/docker-entrypoint-initdb.d/g' restore.sql \
    && apt-get purge -y --auto-remove ca-certificates wget unzip 

