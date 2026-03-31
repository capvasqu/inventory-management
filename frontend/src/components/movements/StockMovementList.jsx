import React, { useState, useEffect } from 'react';
import { stockMovementService } from '../../services/api';

function StockMovementList() {
  const [movements, setMovements] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => { load(); }, []);

  const load = async () => {
    setLoading(true);
    try {
      const res = await stockMovementService.findAll();
      setMovements(res.data);
    } catch (err) {
      setError('Failed to load stock movements');
    } finally {
      setLoading(false);
    }
  };

  const typeColor = (type) => {
    const colors = { IN: '#4caf50', OUT: '#f44336', ADJUSTMENT: '#ff9800' };
    return colors[type] || '#333';
  };

  if (loading) return <p>Loading stock movements...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Stock Movements</h2>
      {/* BUG #27: no filtering by product, warehouse or type — shows all movements at once */}
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
        <thead style={{ background: '#333366', color: '#fff' }}>
          <tr>
            {['Product', 'Warehouse', 'Type', 'Quantity', 'Stock After', 'Reason', 'Reference', 'Date'].map(h => (
              <th key={h} style={{ padding: '10px', textAlign: 'left' }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {movements.map((m, i) => (
            <tr key={m.id} style={{ background: i % 2 === 0 ? '#f9f9f9' : '#fff' }}>
              <td style={{ padding: '8px 10px' }}>{m.product?.name}</td>
              <td style={{ padding: '8px 10px' }}>{m.warehouse?.name}</td>
              <td style={{ padding: '8px 10px' }}>
                <span style={{ color: typeColor(m.movementType), fontWeight: '600' }}>
                  {m.movementType}
                </span>
              </td>
              <td style={{ padding: '8px 10px' }}>{m.quantity}</td>
              <td style={{ padding: '8px 10px' }}>{m.stockAfterMovement}</td>
              <td style={{ padding: '8px 10px' }}>{m.reason || '—'}</td>
              <td style={{ padding: '8px 10px' }}>{m.referenceDocument || '—'}</td>
              <td style={{ padding: '8px 10px' }}>
                {/* BUG #28: date not formatted — shows raw ISO string */}
                {m.createdAt}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default StockMovementList;
