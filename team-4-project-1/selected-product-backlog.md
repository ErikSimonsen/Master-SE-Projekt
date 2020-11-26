#DvdRental - Product Backlog Sprint 1 - 16.04.20
<!--
##Zu erledigen bis 18.03.20
* Datenbank aufsetzen und .sql importieren
* Applicationserver aufsetzen (persistence.xml) und Datenbanktreiber deployen (mit Datenbank verbinden)
    * Über Wildfly CLI auf den Server connecten und:
        * Datenbanktreiber (postgresql jdbc jar) auf Wildfly deployen
        * Data Source bei Wildfly hinzufügen (name = "java:jboss/datasources/dvd-rental"    
        * Tutorial: https://www.squins.com/knowledge/jboss-cli-mysql-datasource-howto/
* Entities erstellen und JPA Relationen passend zur Tabellenstruktur
* Repository/Service Klassen für die Entities (CRUD-Methoden)
* Arquillian Tests für Repository Klassen-->
##Zu erledigen 18.03.20 - 16.04.20
###UI implementieren
* ~~Login Ansicht~~
* ~~Template für obere Leiste (mit Schaltflächen für die jeweilige Verwaltung (Kunden, Filme, Mitarbeiter, Ausleihen))
 On Hover über die einzelnen Schaltflächen werden weitere
 Schaltflächen angezeigt (Kunde erstellen etc.)~~
 * ~~Dollarzeichen überall verwenden statt Eurozeichnen~~
#####Kunden verwalten
* ~~Listenansicht mit allen Kunden und Icons für CRUD Operationen, Sortieren~~
* ~~Kunden anlegen~~
* ~~Kunden löschen~~
* ~~Kunden editieren~~
* ~~Ausleihistorie (Rentals) eines Kunden ansehen mit Zahlungen (Payments)~~
* ~~Suchfunktion über der Listenansicht (Kundenname, Kundennummer)~~
#####Filme verwalten
* ~~Suchfunktion (Filmname, Schauspieler, Kategorie, evtll. Rating (Dropdown))~~  
* ~~Listenansicht mit allen Filmen und Icons für CRUD Operationen, Sortieren~~
* ~~Filme löschen (nicht gefordert, keine Deaktivierung möglich)~~
* ~~Filme anlegen~~
* ~~Filme editieren~~
#####Ausleihen verwalten
* ~~Film ausleihen~~
* ~~Film zurückgeben~~
#####Mitarbeiter verwalten (nur als Store Manager!!)
* ~~Listenansicht~~
* Mitarbeiter anlegen
* Mitarbeiter löschen (deaktivieren)
* Mitarbeiter editieren (Bild anzeigen)
