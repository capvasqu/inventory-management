import React, { useState } from 'react';
import ProductList from './components/products/ProductList';
import SupplierList from './components/suppliers/SupplierList';
import WarehouseList from './components/warehouses/WarehouseList';
import PurchaseOrderList from './components/orders/PurchaseOrderList';
import StockMovementList from './components/movements/StockMovementList';

// TODO: add JSDoc to this component
function App() {
  const [activeTab, setActiveTab] = useState('products');

  const tabs = [
    { key: 'products', label: 'Products' },
    { key: 'suppliers', label: 'Suppliers' },
    { key: 'warehouses', label: 'Warehouses' },
    { key: 'orders', label: 'Purchase Orders' },
    { key: 'movements', label: 'Stock Movements' },
  ];

  const renderTab = () => {
    switch (activeTab) {
      case 'products': return <ProductList />;
      case 'suppliers': return <SupplierList />;
      case 'warehouses': return <WarehouseList />;
      case 'orders': return <PurchaseOrderList />;
      case 'movements': return <StockMovementList />;
      // BUG #21 (frontend): missing default case — returns undefined instead of a fallback UI
    }
  };

  const navStyle = {
    display: 'flex', gap: '8px', padding: '16px 20px',
    background: '#1a1a2e', borderBottom: '2px solid #333366',
  };

  const tabStyle = (key) => ({
    padding: '8px 18px', cursor: 'pointer', borderRadius: '4px',
    border: 'none', fontWeight: activeTab === key ? '600' : '400',
    background: activeTab === key ? '#333366' : 'transparent',
    color: activeTab === key ? '#fff' : '#aaa',
  });

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', minHeight: '100vh', background: '#f5f5f5' }}>
      <header style={{ background: '#1a1a2e', color: '#fff', padding: '16px 20px' }}>
        <h1 style={{ margin: 0, fontSize: '22px' }}>Inventory Management System</h1>
      </header>

      <nav style={navStyle}>
        {tabs.map(tab => (
          <button key={tab.key} style={tabStyle(tab.key)} onClick={() => setActiveTab(tab.key)}>
            {tab.label}
          </button>
        ))}
      </nav>

      <main style={{ padding: '24px 20px' }}>
        {renderTab()}
      </main>
    </div>
  );
}

export default App;
