import React, { useState, useEffect } from 'react';
import { productService } from '../../services/api';

// TODO: add JSDoc to this component
function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    setLoading(true);
    try {
      const res = await productService.findAll();
      setProducts(res.data);
    } catch (err) {
      // BUG #22: generic error message, does not show server error detail
      setError('Failed to load products');
    } finally {
      setLoading(false);
    }
  };

  const handleDeactivate = async (id) => {
    if (!window.confirm('Deactivate this product?')) return;
    try {
      await productService.deactivate(id);
      loadProducts();
    } catch (err) {
      setError('Failed to deactivate product');
    }
  };

  if (loading) return <p>Loading products...</p>;
  // BUG #23: does not show a friendly message when the list is empty
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Products</h2>
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
        <thead style={{ background: '#333366', color: '#fff' }}>
          <tr>
            {['SKU', 'Name', 'Category', 'Unit Price', 'Stock', 'Reorder Level', 'Active', 'Actions'].map(h => (
              <th key={h} style={{ padding: '10px', textAlign: 'left' }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {products.map((p, i) => (
            <tr key={p.id} style={{ background: i % 2 === 0 ? '#f9f9f9' : '#fff' }}>
              <td style={{ padding: '8px 10px' }}>{p.sku}</td>
              <td style={{ padding: '8px 10px' }}>{p.name}</td>
              <td style={{ padding: '8px 10px' }}>{p.category}</td>
              {/* BUG #24: price not formatted as currency */}
              <td style={{ padding: '8px 10px' }}>{p.unitPrice}</td>
              <td style={{ padding: '8px 10px',
                color: p.currentStock <= p.reorderLevel ? 'red' : 'inherit' }}>
                {p.currentStock}
              </td>
              <td style={{ padding: '8px 10px' }}>{p.reorderLevel}</td>
              <td style={{ padding: '8px 10px' }}>{p.active ? 'Yes' : 'No'}</td>
              <td style={{ padding: '8px 10px' }}>
                <button onClick={() => handleDeactivate(p.id)}
                  style={{ padding: '4px 10px', background: '#e24a4a', color: '#fff',
                    border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                  Deactivate
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProductList;
