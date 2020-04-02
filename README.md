# JEE-Server : Gestion de comptes

## Lancement

Pour lancer le serveur avec Maven :

```console
$ mvn spring-boot:run
```

Pour lancer le serveur avec Java :

```console
$ mvn package
$ java -jar target/server-version.jar
```

Pour lancer le serveur avec Docker :

```console
$ docker build . --tag jee-server
$ docker run -d -p 8080:8080 --name="jee-server" jee-server
```

## Description
Serveur JEE permettant de gérer ses dépenses et rentrées d’argent personnelles et de générer des statistiques.

https://github.com/PALA-Organization/JEE-Server

## Fonctionnalités

*Historique de l'ensemble des évènements de l'application (authentification utilisateur, scan de ticket, consultation des comptes par un utilisateur)*

## APIs

### 1. Comptes
- Ajout de transactions à la main de dépenses & rentrées d'argent
- Affichage avec tri par montant, date, période, filtre émetteur/récepteur

### 2. Stats
- Génération de statistiques sur un compte sur une période voulue (mois, jours, semaines) et Optionnel -> Export CSV
- *Pour l'api stats, permettre de remonter le volume d'affaire (totalité des montants à un instant T des comptes, T étant paramétrable via l'appel de l'api)*

### 3. User
- Authentification
- Création de compte basique

### 4. Santé de l’OCR (API Supplémentaire)
*Ajouter une api permettant de connaître l'état de santé du système distant utilisé (posez-vous donc la question de ce que votre application doit faire lorsque l'OCR n'est pas disponible)*

### 5. OCR (API Externe)
Scan des tickets de caisse pour ne pas avoir à rentrer à la main

## Outils
-	MongoDB (NoSQL)
-	AWS avec Load Balancing (load Balancing Apache)
-	CI/CD : GitHub Action pour build/test/deploy
-	Docker

## Working flow
[Git flow](https://danielkummer.github.io/git-flow-cheatsheet/index.fr_FR.html)
