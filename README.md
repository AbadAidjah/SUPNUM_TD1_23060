# TD Partie B: Communication

## Objectif
Cette partie définit et formalise les mécanismes de communication entre les différents services de l'architecture SOA (SOAP, middle-service REST et service consommateur). L'objectif est d'exposer des API REST pour permettre aux clients d'accéder aux détails et au suivi en temps réel de leurs serveurs hébergés dans le datacenter, basé sur un système SOAP existant.

## Contexte
Votre entreprise a développé un système de supervision et de monitoring de serveurs basé sur SOAP (Partie A - Exercice 2). Avec l'expansion vers des services cloud pour des entreprises clientes, il est nécessaire d'exposer des API REST pour que les clients développent leurs propres systèmes de monitoring.

## Architecture
- **Backend SOAP** : Service SOAP exposant des opérations CRUD pour les serveurs (liste, statut, démarrage, arrêt, création, renommage).
- **Middle-Service REST** : Service intermédiaire consommant les endpoints SOAP et exposant des API REST JSON.
- **Service Consommateur** : Service client utilisant Feign pour consommer les API REST du middle-service.

## Services Développés

### 1. Backend SOAP
- **Technologies** : Spring Web Services, JAXB, XSD.
- **Endpoints** :
  - `listServers` : Liste des serveurs.
  - `getServerStatus` : Statut d'un serveur.
  - `startServer` : Démarrer un serveur.
  - `stopServer` : Arrêter un serveur.
  - `createServer` : Créer un serveur.
  - `renameServer` : Renommer un serveur.
  - `deleteServer` : Supprimer un serveur.
- **Port** : 8080
- **WSDL** : Accessible via `http://localhost:8080/ws/servers.wsdl`

### 2. Middle-Service REST
- **Technologies** : Spring Boot, Spring Web Services (client), Maven.
- **Endpoints REST** :
  - `GET /api/servers` : Liste des serveurs (JSON).
  - `GET /api/serverstatus/{id}` : Statut d'un serveur (JSON).
  - `POST /api/startserver/{id}` : Démarrer un serveur (JSON).
  - `POST /api/stopserver/{id}` : Arrêter un serveur (JSON).
  - `POST /api/create/server/` : Créer un serveur (JSON, body: ServerModel).
  - `PUT /api/rename/server/{id}` : Renommer un serveur (JSON, body: ServerModel).
  - `DELETE /api/delete/server/{id}` : Supprimer un serveur.
- **Port** : 8081
- **Communication** : Consomme SOAP via WebServiceTemplate.

### 3. Service Consommateur
- **Technologies** : Spring Boot, Spring Cloud OpenFeign, Lombok.
- **Endpoints REST** :
  - `GET /api/client/servers` : Liste des serveurs.
  - `GET /api/client/servers/{id}/status` : Statut d'un serveur.
  - `POST /api/client/servers/{id}/start` : Démarrer un serveur.
  - `POST /api/client/servers/{id}/stop` : Arrêter un serveur.
  - `POST /api/client/servers/create` : Créer un serveur.
  - `PUT /api/client/servers/{id}/rename` : Renommer un serveur.
  - `DELETE /api/client/servers/{id}/delete` : Supprimer un serveur.
- **Port** : 8082
- **Communication** : Consomme REST via Feign.

## Démarrage des Services

1. **Prérequis** : Docker, Docker Compose, Maven, JDK 17+.
2. **Lancer tout** : `docker compose up -d --build`
3. **Vérifier** : `docker ps` (devrait montrer postgres, backend, middleware, consumer).
4. **Logs** : `docker logs <container-name>` pour debug.

Si tu préfères lancer localement :
- Backend SOAP : `cd soap/demo && mvn spring-boot:run`
- Middleware : `cd rest-middleware/demo && mvn spring-boot:run`
- Consumer : `cd consomateur && mvn spring-boot:run`

## Test des Endpoints

Utilise Thunder Client, Postman ou curl. Exemples :

### SOAP Backend (port 8080)
- Liste serveurs :
  ```bash
  curl -s -H "Content-Type: text/xml;charset=UTF-8" \
    --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://soap.demo.td1Rest.com/servers"><soapenv:Header/><soapenv:Body><ser:listServersRequest/></soapenv:Body></soapenv:Envelope>' \
    http://localhost:8080/ws
  ```

### REST Middleware (port 8081)
- Liste serveurs : `GET http://localhost:8081/api/servers`
- Créer serveur : `POST http://localhost:8081/api/create/server/` avec body JSON `{"name":"test","ipAddress":"10.0.0.1","serverStatus":"INACTIVE"}`

### Consumer (port 8082)
- Liste serveurs : `GET http://localhost:8082/api/client/servers`
- Créer serveur : `POST http://localhost:8082/api/client/servers/create` avec body JSON `{"name":"test","ipAddress":"10.0.0.1","serverStatus":"INACTIVE"}`

Pour plus de détails, check les logs ou le code source.

## Contrats de Communication

### Middle-Service (REST)
- **Format** : JSON
- **Endpoints Détaillés** :
  - `GET /api/servers`
    - Méthode : GET
    - Paramètres : Aucun
    - Réponse : `List<Server>` (id, name, ipAddress, serverStatus)
  - `GET /api/serverstatus/{id}`
    - Méthode : GET
    - Paramètres : id (Long, path)
    - Réponse : `GetServerStatusResponse` (status: ACTIVE/INACTIVE)
  - `POST /api/startserver/{id}`
    - Méthode : POST
    - Paramètres : id (Long, path)
    - Réponse : `StartServerResponse` (server: Server)
  - `POST /api/stopserver/{id}`
    - Méthode : POST
    - Paramètres : id (Long, path)
    - Réponse : `StopServerResponse` (server: Server)
  - `POST /api/create/server/`
    - Méthode : POST
    - Paramètres : Body JSON (ServerModel: name, ipAddress, serverStatus)
    - Réponse : `Server`
  - `PUT /api/rename/server/{id}`
    - Méthode : PUT
    - Paramètres : id (Long, path), Body JSON (ServerModel)
    - Réponse : `Server`

### Service Consommateur (REST)
- **Format** : JSON
- **Endpoints Détaillés** :
  - `GET /api/client/servers`
    - Méthode : GET
    - Paramètres : Aucun
    - Réponse : `List<Server>`
  - `GET /api/client/servers/{id}/status`
    - Méthode : GET
    - Paramètres : id (Long, path)
    - Réponse : `GetServerStatusResponse`
  - `POST /api/client/servers/{id}/start`
    - Méthode : POST
    - Paramètres : id (Long, path)
    - Réponse : `StartServerResponse`
  - `POST /api/client/servers/{id}/stop`
    - Méthode : POST
    - Paramètres : id (Long, path)
    - Réponse : `StopServerResponse`
  - `POST /api/client/servers/create`
    - Méthode : POST
    - Paramètres : Body JSON (Server: name, ipAddress, serverStatus)
    - Réponse : `Server`
  - `PUT /api/client/servers/{id}/rename`
    - Méthode : PUT
    - Paramètres : id (Long, path), Body JSON (Server)
    - Réponse : `Server`

## Comment Lancer

### Prérequis
- Java 17+
- Maven
- Docker (pour docker-compose)

### Lancement via Docker Compose
1. Assurez-vous que `docker-compose.yml` est présent dans le répertoire racine.
2. Exécutez :
   ```bash
   docker-compose up --build
   ```
   Cela lance les trois services :
   - Backend SOAP sur port 8080
   - Middle-Service sur port 8081
   - Consommateur sur port 8082

### Lancement Manuel
1. Backend SOAP : `cd soap/demo && mvn spring-boot:run`
2. Middle-Service : `cd rest-middleware/demo && mvn spring-boot:run`
3. Consommateur : `cd consommateur && mvn spring-boot:run`

## Tests et Requêtes/Réponses pour le Consommateur

Utilisez Thunder Client, Postman ou curl pour tester. Voici des exemples avec curl.

### 1. Lister les Serveurs
```bash
curl -X GET http://localhost:8082/api/client/servers
```
- **Réponse** : `[{"id":1,"name":"Server1","ipAddress":"192.168.1.1","serverStatus":"ACTIVE"}]`

### 2. Obtenir le Statut d'un Serveur
```bash
curl -X GET http://localhost:8082/api/client/servers/1/status
```
- **Réponse** : `{"status":"ACTIVE"}`

### 3. Démarrer un Serveur
```bash
curl -X POST http://localhost:8082/api/client/servers/1/start
```
- **Réponse** : `{"server":{"id":1,"name":"Server1","ipAddress":"192.168.1.1","serverStatus":"ACTIVE"}}`

### 4. Arrêter un Serveur
```bash
curl -X POST http://localhost:8082/api/client/servers/1/stop
```
- **Réponse** : `{"server":{"id":1,"name":"Server1","ipAddress":"192.168.1.1","serverStatus":"INACTIVE"}}`

### 5. Créer un Serveur
```bash
curl -X POST http://localhost:8082/api/client/servers/create \
  -H "Content-Type: application/json" \
  -d '{"name": "NewServer", "ipAddress": "192.168.1.100", "serverStatus": "INACTIVE"}'
```
- **Réponse** : `{"id":2,"name":"NewServer","ipAddress":"192.168.1.100","serverStatus":"INACTIVE"}`

### 6. Renommer un Serveur
```bash
curl -X PUT http://localhost:8082/api/client/servers/1/rename \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name": "RenamedServer", "ipAddress": "192.168.1.1", "serverStatus": "ACTIVE"}'
```
- **Réponse** : `{"id":1,"name":"RenamedServer","ipAddress":"192.168.1.1","serverStatus":"ACTIVE"}`

## Branche et Commit
- Branche : `SOA_TO_REST`
- Les trois services sont regroupés dans ce répertoire.

Autheur : AbadAidjah
Matricule : 23060