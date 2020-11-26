# DvdRental - Product Backlog Sprint 3 - 12.06.2020

## Docker
 - ~~Bridge Netzwerk erstellen (docker network)~~
 - ~~Die drei Microservices aus Sprint 2 via multi stage docker file bauen und in container laufen lassen (siehe https://quarkus.io/guides/building-native-image#creating-a-container-with-a-multi-stage-docker-build)~~
 - ~~Drei Postgres Datenbanken in seperate postgres container laufen lassen. Das jeweilige dump im Dockerfile über den Link von Herrn Müller runterladen (bspw. mit wget oder ADD)~~
 - ~~UI in einem Wildfly container deployen (https://hub.docker.com/r/jboss/wildfly/ (hatten wir schon in Sprint 1 benutzt) oder von Herrn Müller abgucken: https://github.com/BerndMuller/jsf-special/blob/master/src/main/docker/Dockerfile.wildfly)~~
 - ~~Shell script schreiben, was das Netzwerk erstellt, die Images mithilfe der Dockerfiles baut und die Container startet. Nur die Dockerfiles und der Sourcecode der Microservices/UI sollten notwendig sein.~~
 - ~~Evaluieren ob bei DB und Quarkus jeweils nur ein Dockerfile ausreicht.~~
 - ~~Dockerfiles in die Projekte mit ablegen?~~
 - ~~FILM_SERVICE_URI und CUSTOMER_SERVICE_URI in Dockerfiles setzen~~
 - ~~Letzter Schritt: URLs und Ports an Docker-Netzwerk anpassen -> Containernamen statt localhost und Ports anpassen (alle Container können auf Port 8080 lauschen, da 
 Adressierung durch Containernamen)~~

## UI
 - ~~Microservices müssen benutzt werden. Dazu Restanfragen mithilfe des folgenden Clients realisieren: https://docs.oracle.com/javaee/7/tutorial/jaxrs-client001.htm#BABBIHEJ~~
   - ~~Also Repository mit Rest abfragen ersetzen~~
 - ~~Wenn Microservice nicht verfügbar mithilfe das Render Tags "verstecken"~~
 - ~~Welche Tests von Sprint 1 sollte man mit "umziehen" (CustomerRentalsServiceTest) ?~~
 - ~~Errorhandling falls ein rest call mal einen Fehler wirft (also bspw. einen Fehlercode zurückliefert)?~~
 - ~~Pagination base class und vor und zurück Buttons wieder einfügen~~ 
 - ~~Tests für die neuen Microserviceendpunkte hinzufügen~~
 
## Besprechung von 2.6.20
- ~~Mitarbeiterübersicht ?~~
- ~~Kunden Adresse mit anzeigen?~~
~~- Vor und Zurück Buttons?~~
- ~~Filterfelder?~~
- ~~Sortieren der Columns~~
- ~~neuen Film ausleihen~~
- ~~Schauspieler / Kategorien zurzeit nur put zum Hinzufügen. Soll man auch löschen können?~~
- ~~Ausleihen~~

# Verteilung
Beide gleich -> 50:50