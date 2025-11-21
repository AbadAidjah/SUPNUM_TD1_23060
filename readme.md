# Système de Gestion de Serveurs - API SOAP

## Description

Ce projet est une application de gestion de serveurs pour un centre de données, développée dans le cadre du TD1 d'Architecture Orientée Services. L'application expose une API SOAP (Web Services) permettant d'effectuer des opérations complètes sur des ressources serveur, incluant la gestion de leur état opérationnel.

## Architecture

L'application suit une architecture en couches standard :

- **Couche Présentation** : Endpoints SOAP gérant les requêtes XML
- **Couche Métier** : Services contenant la logique applicative
- **Couche Accès aux Données** : Repositories utilisant Spring Data JPA
- **Couche Modèle** : Entités JPA représentant les serveurs
- **Contrat** : Fichier XSD décrivant la structure des messages SOAP

## Technologies Utilisées

- **Java 24**
- **Spring Boot 4.0.0-SNAPSHOT**
- **Spring Web Services (Spring-WS)** - Support SOAP
- **Spring Data JPA** - Gestion de la persistance
- **PostgreSQL 15** - Base de données relationnelle
- **Maven** - Gestionnaire de dépendances et génération de code JAXB
- **JAXB2 Maven Plugin** - Génération des classes Java à partir du XSD
- **Docker & Docker Compose** - Conteneurisation
- **Lombok** - Réduction du code boilerplate

## Prérequis

- Java 24 ou supérieur
- Maven 3.6+
- Docker et Docker Compose
- Git

## Installation et Démarrage

### 1. Génération des classes SOAP à partir du XSD

Après modification du fichier `src/main/resources/servers.xsd`, exécutez :
```bash
mvn clean generate-sources
```
Les classes Java seront générées dans `target/generated-sources/jaxb`.

### 2. Démarrage de l'application

#### Avec Docker Compose (recommandé)

1. Cloner le dépôt :
```bash
git clone https://github.com/AbadAidjah/SUPNUM_TD1_23060.git
cd SUPNUM_TD1_23060 
git checkout SOAP
```
2. Démarrer l'application :
```bash
docker-compose up -d --build
```
3. Vérifier les conteneurs :
```bash
docker-compose ps
```
4. Arrêter l'application :
```bash
docker-compose down
```

#### En local

1. Cloner le dépôt et se placer sur la branche SOAP
2. Créer la base PostgreSQL :
```bash
psql -U postgres -c "CREATE DATABASE td1"
```
3. Configurer `src/main/resources/application.properties`
4. Compiler et lancer :
```bash
./mvnw clean install
./mvnw spring-boot:run
```

## Configuration

### Fichier application.properties

```properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/td1
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## API SOAP - Endpoints

### URL de Base
```
http://localhost:8080/ws
```

### Opérations SOAP disponibles

| Opération         | Requête SOAP (localPart)      | Description                        |
|-------------------|-------------------------------|-------------------------------------|
| listServers       | listServersRequest            | Lister tous les serveurs            |
| createServer      | createServerRequest           | Créer un nouveau serveur            |
| getServerStatus   | getServerStatusRequest        | Obtenir le statut d'un serveur      |
| startServer       | startServerRequest            | Démarrer un serveur                 |
| stopServer        | stopServerRequest             | Arrêter un serveur                  |
| renameServer      | renameServerRequest           | Renommer un serveur                 |
| deleteServer      | deleteServerRequest           | Supprimer un serveur                |

## Exemples de Requêtes SOAP

### 1. Créer un Serveur

**Requête :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ser="http://soap.demo.td1Rest.com/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createServerRequest>
         <ser:server>
            <ser:name>Serveur-Production-01</ser:name>
            <ser:ipAddress>192.168.1.100</ser:ipAddress>
            <ser:serverStatus>ACTIVE</ser:serverStatus>
         </ser:server>
      </ser:createServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Réponse :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ser="http://soap.demo.td1Rest.com/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:createServerResponse>
         <ser:server>
            <ser:id>1</ser:id>
            <ser:name>Serveur-Production-01</ser:name>
            <ser:ipAddress>192.168.1.100</ser:ipAddress>
            <ser:serverStatus>ACTIVE</ser:serverStatus>
         </ser:server>
         <ser:serverId>1</ser:serverId>
         <ser:success>true</ser:success>
         <ser:name>Serveur-Production-01</ser:name>
         <ser:ipAddress>192.168.1.100</ser:ipAddress>
         <ser:serverStatus>ACTIVE</ser:serverStatus>
      </ser:createServerResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

### 2. Lister Tous les Serveurs

**Requête :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ser="http://soap.demo.td1Rest.com/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:listServersRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Réponse :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ser="http://soap.demo.td1Rest.com/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:listServersResponse>
         <ser:server>
            <ser:id>1</ser:id>
            <ser:name>Serveur-Production-01</ser:name>
            <ser:ipAddress>192.168.1.100</ser:ipAddress>
            <ser:serverStatus>ACTIVE</ser:serverStatus>
         </ser:server>
         <!-- ... -->
      </ser:listServersResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

## Structure du Projet

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/td1Rest/demo/
│   │   │       ├── endpoint/         # Endpoints SOAP
│   │   │       ├── model/            # Entités JPA
│   │   │       ├── repository/       # Accès BDD
│   │   │       ├── service/          # Logique métier
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── servers.xsd           # Contrat SOAP
│   └── test/
├── target/generated-sources/jaxb/     # Classes générées depuis le XSD
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## Schéma de la Base de Données

| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | BIGSERIAL | PRIMARY KEY |
| name | VARCHAR(255) | NOT NULL |
| ip_address | VARCHAR(50) | UNIQUE |
| server_status | VARCHAR(20) | NOT NULL |

## Règles Métier

1. **Création de serveur** : Tous les champs sont requis
2. **Démarrage** : Change le statut du serveur à ACTIVE
3. **Arrêt** : Change le statut du serveur à INACTIVE
4. **Suppression** : Possible uniquement si le serveur est à l'état INACTIVE
5. **Renommage** : Possible quel que soit l'état du serveur

## Gestion des Erreurs

L'API retourne les codes SOAP standards (faults) en cas d'erreur métier ou technique.

## Dépannage

### Génération des classes JAXB
```bash
mvn clean generate-sources
```

### Problèmes de port ou de base de données
- Vérifier que le port 8080 est libre
- Vérifier que PostgreSQL est démarré
- Vérifier la configuration dans `application.properties`

## Auteur

**Matricule** : Abad Aidjah  
**Branche** : SOAP  
**Établissement** : SUPNUM

## Licence

Ce projet est réalisé dans un cadre académique pour l'apprentissage du développement d'APIs SOAP avec Spring Boot.

---

**Date de dernière mise à jour** : 21 Novembre 2025
