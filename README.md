# Inventory Management System

Spring Boot microservice + React frontend for managing products, suppliers,
warehouses, purchase orders and stock movements.

Built as a practice project for AI-driven development with Claude Code and Cursor.

---

## About this project

A realistic multi-module inventory system designed to practice:
- Code review with AI agents (Claude Code)
- Bug identification and fixing with Cursor
- Test generation for untested code
- Documentation generation from source code

**Tools used:** Claude Code · Cursor · Spring Boot · React · Docker · MySQL

---

## Modules

| Module | Description |
|--------|-------------|
| **Products** | SKU, name, category, price, stock level, reorder alerts |
| **Suppliers** | Company info, contact details, tax ID |
| **Warehouses** | Location, capacity, occupancy tracking |
| **Purchase Orders** | Full lifecycle: DRAFT → SUBMITTED → APPROVED → RECEIVED |
| **Stock Movements** | IN / OUT / ADJUSTMENT with full audit trail |

---

## Project structure

```
inventory-management/
├── src/
│   ├── main/java/com/demo/inventory/
│   │   ├── controller/      5 REST controllers
│   │   ├── service/         5 services (18 intentional bugs)
│   │   ├── repository/      5 JPA repositories
│   │   ├── model/           6 JPA entities
│   │   ├── dto/             InventoryDTOs.java
│   │   └── exception/       ResourceNotFoundException, BusinessException,
│   │                        GlobalExceptionHandler
│   └── test/                JUnit 5 tests + TODO cases
├── frontend/
│   └── src/
│       ├── App.jsx                              (BUG #21)
│       ├── services/api.js                      (BUG #19, #20)
│       └── components/
│           ├── products/ProductList.jsx          (BUG #22, #23, #24)
│           ├── suppliers/SupplierList.jsx
│           ├── warehouses/WarehouseList.jsx
│           ├── orders/PurchaseOrderList.jsx      (BUG #25, #26)
│           └── movements/StockMovementList.jsx   (BUG #27, #28)
├── Dockerfile
├── docker-compose.yml
├── .cursorrules
└── README.md
```

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+
- Docker and Docker Compose (optional)
- MySQL 8.0 (or use Docker)

---

## Run with Docker Compose (recommended)

```bash
docker-compose up --build
```

- Frontend: http://localhost:3000
- REST API: http://localhost:8080/api

---

## Run manually

### 1. Create the database

```sql
CREATE DATABASE inventory_db;
```

Update credentials in `src/main/resources/application.properties`

### 2. Backend

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

---

## API endpoints

### Products `/api/products`
| Method | URL | Description |
|--------|-----|-------------|
| GET | /products | List all products |
| GET | /products/{id} | Find by ID |
| GET | /products/sku/{sku} | Find by SKU |
| POST | /products | Create product |
| PUT | /products/{id} | Update product |
| DELETE | /products/{id} | Deactivate product |
| GET | /products/category/{category} | Filter by category |
| GET | /products/reorder-alert | Products below reorder level |
| GET | /products/low-stock?threshold=N | Products with stock below N |
| PATCH | /products/{id}/price?newPrice=N | Update unit price |

### Suppliers `/api/suppliers`
| Method | URL | Description |
|--------|-----|-------------|
| GET | /suppliers | List all suppliers |
| GET | /suppliers/{id} | Find by ID |
| POST | /suppliers | Create supplier |
| PUT | /suppliers/{id} | Update supplier |
| DELETE | /suppliers/{id} | Deactivate supplier |
| GET | /suppliers/search?name=X | Search by company name |

### Warehouses `/api/warehouses`
| Method | URL | Description |
|--------|-----|-------------|
| GET | /warehouses | List all warehouses |
| GET | /warehouses/{id} | Find by ID |
| POST | /warehouses | Create warehouse |
| PUT | /warehouses/{id} | Update warehouse |
| DELETE | /warehouses/{id} | Deactivate warehouse |
| GET | /warehouses/available | Warehouses with available capacity |

### Purchase Orders `/api/purchase-orders`
| Method | URL | Description |
|--------|-----|-------------|
| GET | /purchase-orders | List all orders |
| GET | /purchase-orders/{id} | Find by ID |
| POST | /purchase-orders | Create order |
| PATCH | /purchase-orders/{id}/status?status=X | Update status |
| GET | /purchase-orders/status/{status} | Filter by status |
| GET | /purchase-orders/supplier/{supplierId} | Orders by supplier |

### Stock Movements `/api/stock-movements`
| Method | URL | Description |
|--------|-----|-------------|
| GET | /stock-movements | List all movements |
| POST | /stock-movements | Record a movement (IN/OUT/ADJUSTMENT) |
| GET | /stock-movements/product/{productId} | Movements by product |
| GET | /stock-movements/warehouse/{warehouseId} | Movements by warehouse |

---

## Practice exercises with Cursor

### Exercise 1 — Identify all bugs (Agent mode)
In Claude Code prompt:
```
Analyze the full project and generate a BUG-REPORT.md listing all
comments marked as BUG # with file, line, description and suggested fix.
```

### Exercise 2 — Fix critical stock bugs
In Cursor chat:
```
@StockMovementService.java
Fix BUG #11, #12 and #13. Ensure stock can never go negative,
support negative quantity in ADJUSTMENT type, and validate
stock before OUT movements.
```

### Exercise 3 — Fix status transition bug
```
@PurchaseOrderService.java
Fix BUG #16. Implement valid status transition rules:
DRAFT → SUBMITTED → APPROVED → RECEIVED
CANCELLED only from DRAFT or SUBMITTED.
Throw BusinessException for invalid transitions.
```

### Exercise 4 — Complete missing tests
```
@ProductServiceTest.java @ProductService.java
Implement all tests marked as TODO following the existing test style.
Cover: duplicate SKU on create, null id validation, negative price.
```

### Exercise 5 — Generate full documentation
```
Analyze the project and generate README.md with architecture diagram
in Mermaid, ARCHITECTURE.md with module relationships, and
CHANGELOG.md in Keep a Changelog format.
```
