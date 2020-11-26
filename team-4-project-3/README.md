# Starten der Anwendung

Zum Starten der Anwendung das build-projects.sh Script ausführen. Es muss sichergestellt werden, dass keine existierenden Container vorhanden sind. Also bspw. folgendes Kommando ausführen.

    docker system prune -a
    
Außerdem müssen die Ports 8080 und 9990 frei sein.
    
Das Shell-Script findet sich im Root-Verzeichnis des Projekts. Das Script verlangt root-Rechte.

    sudo ./build-projects.sh

Die Anwendung ist nach erfolgreichem durchlaufen des Shell-Scripts unter folgender Adresse auffindbar:

    http://localhost:8080/dvdrental/ 