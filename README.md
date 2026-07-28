# 🏦 EGWallet Backend

Backend API REST pour EGWallet - Application de Gestion de Finances Personnelles

## 🚀 Démarrage Rapide

### Prérequis
- Java 17 LTS
- Maven 3.9+
- PostgreSQL 15+ (Production)
- Docker & Docker Compose (Optionnel)

### Installation

```bash
# Cloner le repository
git clone https://github.com/RODRIPONE/EGwallet-server.git
cd EGwallet-server

# Installer les dépendances
mvn clean install

# Lancer avec Docker Compose
docker-compose up -d

# Lancer l'application
mvn spring-boot:run
```

### Configuration

Copier `.env.example` en `.env` et configurer les variables:

```bash
cp .env.example .env
```

L'application démarre sur: `http://localhost:8080/api`

## 📚 Documentation API

La documentation Swagger est disponible à: `http://localhost:8080/api/swagger-ui.html`

## 📁 Structure du Projet

```
egwallet-backend/
├── src/
│   ├── main/
│   │   ├── java/com/egwallet/
│   │   │   ├── config/          # Configuration beans
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── service/          # Business Logic
│   │   │   ├── repository/       # Data Access
│   │   │   ├── entity/           # JPA Entities
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Custom Exceptions
│   │   │   ├── security/         # Security & JWT
│   │   │   ├── validator/        # Business Validators
│   │   │   ├── mapper/           # MapStruct Mappers
│   │   │   ├── utils/            # Utilities
│   │   │   ├── scheduler/        # Scheduled Tasks
│   │   │   └── EgwalletApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-prod.yml
│   │       └── db/migration/
│   └── test/
├── docker/
│   ├── Dockerfile
│   ├── Dockerfile.postgres
│   └── docker-compose.yml
├── pom.xml
└── README.md
```

## 🔐 Sécurité

- JWT Token-based Authentication
- CORS configuré
- Input validation stricte
- Rate limiting
- Password encryption (BCrypt)

## 🧪 Tests

```bash
# Lancer tous les tests
mvn test

# Lancer les tests avec couverture
mvn test jacoco:report
```

## 📝 Contributeurs

- RODRIPONE

## 📄 License

MIT
