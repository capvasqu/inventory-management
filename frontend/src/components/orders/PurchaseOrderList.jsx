import React, { useState, useEffect } from 'react';
import { purchaseOrderService } from '../../services/api';

function PurchaseOrderList() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => { load(); }, []);

  const load = async () => {
    setLoading(true);
    try {
      const res = await purchaseOrderService.findAll();
      setOrders(res.data);
    } catch (err) {
      setError('Failed to load purchase orders');
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (id, newStatus) => {
    try {
      await purchaseOrderService.updateStatus(id, newStatus);
      load();
    } catch (err) {
      // BUG #25: no feedback shown to user when status update fails
      console.error(err);
    }
  };

  const statusColor = (status) => {
    const colors = {
      DRAFT: '#888', SUBMITTED: '#2196f3',
      APPROVED: '#ff9800', RECEIVED: '#4caf50', CANCELLED: '#f44336'
    };
    return colors[status] || '#333';
  };

  if (loading) return <p>Loading purchase orders...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Purchase Orders</h2>
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
        <thead style={{ background: '#333366', color: '#fff' }}>
          <tr>
            {['Order #', 'Supplier', 'Status', 'Expected Delivery', 'Total', 'Actions'].map(h => (
              <th key={h} style={{ padding: '10px', textAlign: 'left' }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {orders.map((o, i) => (
            <tr key={o.id} style={{ background: i % 2 === 0 ? '#f9f9f9' : '#fff' }}>
              <td style={{ padding: '8px 10px' }}>{o.orderNumber}</td>
              <td style={{ padding: '8px 10px' }}>{o.supplier?.companyName}</td>
              <td style={{ padding: '8px 10px' }}>
                <span style={{ color: statusColor(o.status), fontWeight: '600' }}>
                  {o.status}
                </span>
              </td>
              <td style={{ padding: '8px 10px' }}>{o.expectedDeliveryDate || '—'}</td>
              {/* BUG #26: total amount not formatted as currency */}
              <td style={{ padding: '8px 10px' }}>{o.totalAmount}</td>
              <td style={{ padding: '8px 10px', display: 'flex', gap: '4px' }}>
                {o.status === 'DRAFT' && (
                  <button onClick={() => handleStatusChange(o.id, 'SUBMITTED')}
                    style={{ padding: '4px 8px', background: '#2196f3', color: '#fff',
                      border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '12px' }}>
                    Submit
                  </button>
                )}
                {o.status === 'SUBMITTED' && (
                  <button onClick={() => handleStatusChange(o.id, 'APPROVED')}
                    style={{ padding: '4px 8px', background: '#ff9800', color: '#fff',
                      border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '12px' }}>
                    Approve
                  </button>
                )}
                {o.status === 'APPROVED' && (
                  <button onClick={() => handleStatusChange(o.id, 'RECEIVED')}
                    style={{ padding: '4px 8px', background: '#4caf50', color: '#fff',
                      border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '12px' }}>
                    Mark Received
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PurchaseOrderList;
