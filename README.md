# GoogleFeudDE
Vor dem Start: 
  -lokalen Datenbank-Server starten
  -Datenbank anlegen
  -Table anlegen:
    create table bestenliste ( id int PRIMARY KEY AUTO_INCREMENT, spieler_name varchar(255) NOT NULL UNIQUE, punkte int);
  -Datenbank-Name, User und Passwort in glassfish-resources.xml hinterlegen
