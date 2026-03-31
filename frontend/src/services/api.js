import axios from 'axios';

// TODO: move baseURL to environment variable (.env)
const API = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
});

// ── Products ──────────────────────────────────────────────
export const productService = {
  findAll: () => API.get('/products'),
  findById: (id) => API.get(`/products/${id}`),
  findBySku: (sku) => API.get(`/products/sku/${sku}`),
  create: (data) => API.post('/products', data),
  update: (id, data) => API.put(`/products/${id}`, data),
  // BUG #19 (frontend): should be DELETE, not GET
  deactivate: (id) => API.get(`/products/${id}`),
  findByCategory: (cat) => API.get(`/products/category/${cat}`),
  findBelowReorderLevel: () => API.get('/products/reorder-alert'),
  findLowStock: (threshold) => API.get(`/products/low-stock?threshold=${threshold}`),
  adjustPrice: (id, price) => API.patch(`/products/${id}/price?newPrice=${price}`),
};

// ── Suppliers ─────────────────────────────────────────────
export const supplierService = {
  findAll: () => API.get('/suppliers'),
  findById: (id) => API.get(`/suppliers/${id}`),
  create: (data) => API.post('/suppliers', data),
  update: (id, data) => API.put(`/suppliers/${id}`, data),
  deactivate: (id) => API.delete(`/suppliers/${id}`),
  search: (name) => API.get(`/suppliers/search?name=${name}`),
};

// ── Warehouses ────────────────────────────────────────────
export const warehouseService = {
  findAll: () => API.get('/warehouses'),
  findById: (id) => API.get(`/warehouses/${id}`),
  create: (data) => API.post('/warehouses', data),
  update: (id, data) => API.put(`/warehouses/${id}`, data),
  deactivate: (id) => API.delete(`/warehouses/${id}`),
  findAvailable: () => API.get('/warehouses/available'),
};

// ── Purchase Orders ───────────────────────────────────────
export const purchaseOrderService = {
  findAll: () => API.get('/purchase-orders'),
  findById: (id) => API.get(`/purchase-orders/${id}`),
  create: (data) => API.post('/purchase-orders', data),
  updateStatus: (id, status) => API.patch(`/purchase-orders/${id}/status?status=${status}`),
  findByStatus: (status) => API.get(`/purchase-orders/status/${status}`),
  // BUG #20 (frontend): missing error handling when supplier does not exist
  findBySupplier: (supplierId) => API.get(`/purchase-orders/supplier/${supplierId}`),
};

// ── Stock Movements ───────────────────────────────────────
export const stockMovementService = {
  findAll: () => API.get('/stock-movements'),
  record: (data) => API.post('/stock-movements', data),
  findByProduct: (productId) => API.get(`/stock-movements/product/${productId}`),
  findByWarehouse: (warehouseId) => API.get(`/stock-movements/warehouse/${warehouseId}`),
};
