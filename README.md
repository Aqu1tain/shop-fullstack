# Shop Application - Full-Stack Project

Application e-commerce avec backend Spring Boot et frontend Vue.js.

## Structure du projet

```
TP-15-Jan/
├── backend/          # API Spring Boot
└── frontend/         # Application Vue.js
```

## Fonctionnalites

- Authentification JWT (inscription, connexion)
- Gestion des produits (CRUD)
- Gestion des categories
- Systeme de commandes
- Backoffice administrateur
- Tableau de bord avec statistiques

## Base de donnees

### Schema

```
users
├── id (PK)
├── username (unique)
├── email (unique)
├── password
├── role (USER, ADMIN)
├── enabled
└── created_at

categories
├── id (PK)
└── name (unique)

products
├── id (PK)
├── name
├── description
├── price
├── category_id (FK -> categories)
├── stock_quantity
├── image_url
└── created_at

orders
├── id (PK)
├── user_id (FK -> users)
├── product_id (FK -> products)
├── quantity
├── order_date
├── total_amount
└── status (PENDING, PROCESSING, SHIPPED, DELIVERED)
```

## Installation

### Prerequisites

- Java 17+
- Node.js 18+
- PostgreSQL
- Maven

### Backend

1. Creer la base de donnees PostgreSQL :
```sql
CREATE DATABASE shopdb;
```

2. Configurer `backend/src/main/resources/application.properties` si necessaire

3. Lancer le backend :
```bash
cd backend
./mvnw spring-boot:run
```

Le serveur demarre sur http://localhost:8080

### Frontend

```bash
cd frontend
npm install
npm run dev
```

L'application demarre sur http://localhost:3000

## Comptes par defaut

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |

## API Endpoints

### Authentification (public)
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion

### Produits (public)
- `GET /api/products` - Liste paginee
- `GET /api/products/{id}` - Detail
- `GET /api/products/search?query=` - Recherche
- `GET /api/products/category/{id}` - Par categorie
- `GET /api/products/categories` - Liste des categories

### Commandes (authentifie)
- `POST /api/orders` - Creer une commande
- `GET /api/orders/my-orders` - Mes commandes

### Utilisateur (authentifie)
- `GET /api/user/profile` - Mon profil

### Administration (ADMIN)
- `GET /api/admin/dashboard` - Statistiques
- `GET /api/admin/users` - Liste utilisateurs
- `PUT /api/admin/users/{id}` - Modifier utilisateur
- `GET/POST /api/admin/products` - CRUD produits
- `PUT /api/admin/products/{id}` - Modifier produit
- `DELETE /api/admin/products/{id}` - Supprimer produit
- `GET /api/admin/orders` - Liste commandes
- `PUT /api/admin/orders/{id}/status` - Changer statut

## Technologies

### Backend
- Spring Boot 3.2
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Lombok

### Frontend
- Nuxt 4.2
- Nuxt UI v4 (component library)
- Vue.js 3
- TailwindCSS
