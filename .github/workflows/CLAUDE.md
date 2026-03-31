# Inventory Management System — Claude Context

## Project overview
Spring Boot 3.2 + React 18 inventory management system with 5 modules:
Products, Suppliers, Warehouses, Purchase Orders and Stock Movements.

## Critical business rules
- Stock can NEVER go negative — any OUT movement must validate available stock first
- Purchase order status transitions: DRAFT → SUBMITTED → APPROVED → RECEIVED
  CANCELLED only allowed from DRAFT or SUBMITTED
- SKU must be unique across all products
- All monetary values use BigDecimal — never double or float
- Soft delete only (active = false) — never deleteById()

## Architecture
- controller/ — receives HTTP requests, delegates to service, no business logic
- service/    — all business logic lives here, @Transactional when modifying multiple entities
- repository/ — JPA interfaces only
- model/      — JPA entities, no business logic
- dto/        — request/response objects, entities never exposed directly
- exception/  — ResourceNotFoundException, BusinessException, GlobalExceptionHandler

## Known intentional bugs (for practice)
- BUG #2–#9: ProductService — field injection, missing validations, physical delete
- BUG #10–#14: StockMovementService — stock can go negative, adjustment logic broken
- BUG #15–#18: PurchaseOrderService — lineTotal not recalculated, no status transition validation

## When reviewing PRs
- Flag any change that touches StockMovementService — high risk of breaking stock integrity
- Flag any change to PurchaseOrder status logic — transitions must be validated
- Flag any use of double/float for monetary calculations
- Flag any deleteById() call — soft delete required
- Flag missing @Transactional on methods that modify multiple entities