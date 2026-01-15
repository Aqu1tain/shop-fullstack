# Shop Fullstack

E-commerce app with Spring Boot backend + Nuxt UI frontend.

## Quick Start

### Prerequisites
- Java 17+, Node 18+, PostgreSQL, Maven

### Database
```sql
CREATE DATABASE shopdb;
```

### Run
```bash
# Backend (port 8080)
cd backend && ./mvnw spring-boot:run

# Frontend (port 3000)
cd frontend && npm install && npm run dev
```

### Default Admin
`admin` / `admin123`

## API

| Endpoint | Access | Description |
|----------|--------|-------------|
| `POST /api/auth/register` | Public | Register |
| `POST /api/auth/login` | Public | Login (returns JWT) |
| `GET /api/products` | Public | List products (paginated) |
| `GET /api/products/{id}` | Public | Product detail |
| `GET /api/products/search?query=` | Public | Search |
| `GET /api/products/category/{id}` | Public | Filter by category |
| `POST /api/orders` | Auth | Create order |
| `GET /api/orders/my-orders` | Auth | User's orders |
| `GET /api/user/profile` | Auth | User profile |
| `GET /api/admin/dashboard` | Admin | Stats |
| `GET/POST/PUT/DELETE /api/admin/products` | Admin | CRUD |
| `GET/PUT /api/admin/users` | Admin | Manage users |
| `GET /api/admin/orders` | Admin | All orders |
| `PUT /api/admin/orders/{id}/status` | Admin | Update status |

## Schema

```
users: id, username, email, password, role, enabled, created_at
categories: id, name
products: id, name, description, price, category_id, stock_quantity, image_url, created_at
orders: id, user_id, product_id, quantity, order_date, total_amount, status
```

## Stack

**Backend:** Spring Boot 3.2, Spring Security (JWT), JPA, PostgreSQL
**Frontend:** Nuxt 4.2, Nuxt UI v4, TailwindCSS
