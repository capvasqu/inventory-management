import React, { useState, useEffect } from 'react';
import { warehouseService } from '../../services/api';

function WarehouseList() {
  const [warehouses, setWarehouses] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => { load(); }, []);

  const load = async () => {
    setLoading(true);
    try {
      const res = await warehouseService.findAll();
      setWarehouses(res.data);
    } finally {
      setLoading(false);
    }
  };

  const occupancyPercent = (w) =>
    w.capacity > 0 ? Math.round((w.currentOccupancy / w.capacity) * 100) : 0;

  if (loading) return <p>Loading warehouses...</p>;

  return (
    <div>
      <h2>Warehouses</h2>
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
        <thead style={{ background: '#333366', color: '#fff' }}>
          <tr>
            {['Code', 'Name', 'Location', 'Capacity', 'Occupancy', 'Usage %', 'Active'].map(h => (
              <th key={h} style={{ padding: '10px', textAlign: 'left' }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {warehouses.map((w, i) => (
            <tr key={w.id} style={{ background: i % 2 === 0 ? '#f9f9f9' : '#fff' }}>
              <td style={{ padding: '8px 10px' }}>{w.code}</td>
              <td style={{ padding: '8px 10px' }}>{w.name}</td>
              <td style={{ padding: '8px 10px' }}>{w.location}</td>
              <td style={{ padding: '8px 10px' }}>{w.capacity}</td>
              <td style={{ padding: '8px 10px' }}>{w.currentOccupancy}</td>
              <td style={{ padding: '8px 10px',
                color: occupancyPercent(w) >= 90 ? 'red' : 'inherit' }}>
                {occupancyPercent(w)}%
              </td>
              <td style={{ padding: '8px 10px' }}>{w.active ? 'Yes' : 'No'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default WarehouseList;
