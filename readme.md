# Système de Gestion de Serveurs - API REST

## Description

Ce projet est une application de gestion de serveurs pour un centre de données, développée dans le cadre du TD1 d'Architecture Orientée Services. L'application expose une API RESTful permettant d'effectuer des opérations CRUD complètes sur des ressources serveur, incluant la gestion de leur état opérationnel.

## Architecture

L'application suit une architecture en couches standard :

- **Couche Présentation** : Contrôleurs REST gérant les requêtes HTTP
- **Couche Métier** : Services contenant la logique applicative
- **Couche Accès aux Données** : Repositories utilisant Spring Data JPA
- **Couche Modèle** : Entités JPA représentant les serveurs

## Technologies Utilisées

- **Java 24**
- **Spring Boot 4.0.0-SNAPSHOT**
- **Spring Data JPA** - Gestion de la persistance
- **PostgreSQL 15** - Base de données relationnelle
- **Maven** - Gestionnaire de dépendances
- **Docker & Docker Compose** - Conteneurisation
- **SpringDoc OpenAPI** - Documentation interactive de l'API
- **Lombok** - Réduction du code boilerplate

## Prérequis

- Java 24 ou supérieur
- Maven 3.6+
- Docker et Docker Compose
- Git

## Installation et Démarrage

### Option 1 : Avec Docker Compose (Recommandé)

Docker Compose est un outil permettant de définir et d'exécuter des applications multi-conteneurs. Il utilise un fichier YAML pour configurer les services de l'application, puis crée et démarre tous les services avec une seule commande.

**Étapes :**

1. Cloner le dépôt :
```bash
git clone https://github.com/AbadAidjah/SUPNUM_TD1_23060.git
cd SUPNUM_TD1_23060 
git checkout REST
```

2. Démarrer l'application avec Docker Compose :
```bash
docker-compose up -d --build
```

Cette commande va :
- Construire l'image Docker de l'application Spring Boot
- Démarrer un conteneur PostgreSQL
- Créer automatiquement la base de données
- Démarrer l'application sur le port 8080

3. Vérifier que les conteneurs sont actifs :
```bash
docker-compose ps
```

4. Accéder à l'application :
- API : http://localhost:8080
- Swagger UI : http://localhost:8080/swagger-ui.html

5. Arrêter l'application :
```bash
docker-compose down
```

### Option 2 : Installation Locale

1. Cloner le dépôt :
```bash
git clone https://github.com/AbadAidjah/SUPNUM_TD1_23060.git
cd SUPNUM_TD1_23060
git checkout REST
```

2. Créer la base de données PostgreSQL :
```bash
psql -U postgres -c "CREATE DATABASE td1"
```

3. Configurer les paramètres de connexion dans `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/td1
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
```

4. Compiler et exécuter l'application :
```bash
./mvnw clean install
./mvnw spring-boot:run
```

## Configuration

### Fichier application.properties

```properties
# Nom de l'application
spring.application.name=demo

# Configuration PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/td1
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuration JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Configuration Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

## API REST - Endpoints

### URL de Base
```
http://localhost:8080/api/rest
```

### Liste des Endpoints

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/create` | Créer un nouveau serveur |
| GET | `/list` | Obtenir tous les serveurs |
| GET | `/server/{id}/status` | Obtenir le statut d'un serveur |
| PATCH | `/server/{id}/start` | Démarrer un serveur |
| PATCH | `/server/{id}/stop` | Arrêter un serveur |
| PUT | `/server/{id}/rename` | Renommer un serveur |
| DELETE | `/server/{id}` | Supprimer un serveur |

## Exemples d'Utilisation

### 1. Créer un Serveur

**Requête :**
```http
POST http://localhost:8080/api/rest/create
Content-Type: application/json

{
  "name": "Serveur-Production-01",
  "ipAddress": "192.168.1.100",
  "serverStatus": "ACTIVE"
}
```

**Réponse (201 CREATED) :**
```json
{
  "id": 1,
  "name": "Serveur-Production-01",
  "ipAddress": "192.168.1.100",
  "serverStatus": "ACTIVE"
}
```

### 2. Lister Tous les Serveurs

**Requête :**
```http
GET http://localhost:8080/api/rest/list
```

**Réponse (200 OK) :**
```json
[
  {
    "id": 1,
    "name": "Serveur-Production-01",
    "ipAddress": "192.168.1.100",
    "serverStatus": "ACTIVE"
  },
  {
    "id": 2,
    "name": "Serveur-Dev-01",
    "ipAddress": "192.168.1.101",
    "serverStatus": "INACTIVE"
  }
]
```

### 3. Obtenir le Statut d'un Serveur

**Requête :**
```http
GET http://localhost:8080/api/rest/server/1/status
```

**Réponse (200 OK) :**
```json
"ACTIVE"
```

### 4. Démarrer un Serveur

**Requête :**
```http
PATCH http://localhost:8080/api/rest/server/1/start
```

**Réponse (200 OK) :**
```json
{
  "id": 1,
  "name": "Serveur-Production-01",
  "ipAddress": "192.168.1.100",
  "serverStatus": "ACTIVE"
}
```

### 5. Arrêter un Serveur

**Requête :**
```http
PATCH http://localhost:8080/api/rest/server/1/stop
```

**Réponse (200 OK) :**
```json
{
  "id": 1,
  "name": "Serveur-Production-01",
  "ipAddress": "192.168.1.100",
  "serverStatus": "INACTIVE"
}
```

### 6. Renommer un Serveur

**Requête :**
```http
PUT http://localhost:8080/api/rest/server/1/rename?name=Nouveau-Nom-Serveur
```

**Réponse (200 OK) :**
```json
{
  "id": 1,
  "name": "Nouveau-Nom-Serveur",
  "ipAddress": "192.168.1.100",
  "serverStatus": "INACTIVE"
}
```

### 7. Supprimer un Serveur

**Note :** Le serveur doit être à l'état INACTIVE pour être supprimé.

**Requête :**
```http
DELETE http://localhost:8080/api/rest/server/1
```

**Réponse (204 NO CONTENT) :**
```
(Pas de contenu)
```

**Cas d'erreur - Serveur actif :**
```json
{
  "timestamp": "2025-11-18T10:30:00.000Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Cannot delete a running server! Stop it first.",
  "path": "/api/rest/server/1"
}
```

## Modèle de Données

### Entité Server

| Champ | Type | Description | Contraintes |
|-------|------|-------------|-------------|
| id | Long | Identifiant unique | Clé primaire, auto-généré |
| name | String | Nom du serveur | Non null |
| ipAddress | String | Adresse IP | Unique |
| serverStatus | Enum | État du serveur | ACTIVE ou INACTIVE |

### États Possibles (Enum Status)

- **ACTIVE** : Le serveur est en cours d'exécution
- **INACTIVE** : Le serveur est arrêté

## Documentation Interactive

Une fois l'application démarrée, accédez à la documentation interactive Swagger :

- **Interface Swagger UI** : http://localhost:8080/swagger-ui.html
- **Spécification OpenAPI JSON** : http://localhost:8080/api-docs

La documentation Swagger permet de :
- Visualiser tous les endpoints disponibles
- Tester les APIs directement depuis le navigateur
- Consulter les schémas de requêtes et réponses
- Comprendre les codes de statut HTTP retournés

## Structure du Projet

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/td1Rest/demo/
│   │   │       ├── controller/
│   │   │       │   └── ServerController.java
│   │   │       ├── model/
│   │   │       │   └── ServerModel.java
│   │   │       ├── repository/
│   │   │       │   └── ServerRepository.java
│   │   │       ├── service/
│   │   │       │   └── ServerService.java
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## Schéma de la Base de Données

### Table : servers

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

L'API retourne les codes HTTP standards :

- **200 OK** : Requête réussie
- **201 CREATED** : Ressource créée avec succès
- **204 NO CONTENT** : Suppression réussie
- **400 BAD REQUEST** : Données invalides
- **404 NOT FOUND** : Ressource non trouvée
- **500 INTERNAL SERVER ERROR** : Erreur serveur

## Tests avec cURL

```bash
# Créer un serveur
curl -X POST http://localhost:8080/api/rest/create \
  -H "Content-Type: application/json" \
  -d '{"name":"Test-Server","ipAddress":"10.0.0.1","serverStatus":"ACTIVE"}'

# Lister les serveurs
curl http://localhost:8080/api/rest/list

# Obtenir le statut
curl http://localhost:8080/api/rest/server/1/status

# Arrêter un serveur
curl -X PATCH http://localhost:8080/api/rest/server/1/stop

# Renommer un serveur
curl -X PUT "http://localhost:8080/api/rest/server/1/rename?name=Nouveau-Nom"

# Supprimer un serveur
curl -X DELETE http://localhost:8080/api/rest/server/1
```

## Dépannage

### Le port 8080 est déjà utilisé

```bash
# Identifier le processus
lsof -ti:8080

# Terminer le processus
kill -9 $(lsof -ti:8080)
```

### Problèmes de connexion à la base de données

```bash
# Vérifier que PostgreSQL est en cours d'exécution
docker ps | grep postgres

# Se connecter à la base de données
psql -h localhost -p 5432 -U postgres -d td1
```

### Erreurs de compilation Maven

```bash
# Nettoyer et recompiler
./mvnw clean install

# Ignorer les tests
./mvnw clean install -DskipTests
```

## Auteur

**Matricule** : Abad Aidjah  
**Branche** : REST  
**Établissement** : SUPNUM

## Licence

Ce projet est réalisé dans un cadre académique pour l'apprentissage du développement d'APIs RESTful avec Spring Boot.

---

**Date de dernière mise à jour** : 18 Novembre 2025
