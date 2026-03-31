import React, { useState, useEffect } from 'react';
import { supplierService } from '../../services/api';

export function SupplierList() {
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => { load(); }, []);

  const load = async () => {
    setLoading(true);
    try {
      const res = await supplierService.findAll();
      setSuppliers(res.data);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading suppliers...</p>;

  return (
    <div>
      <h2>Suppliers</h2>
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
        <thead style={{ background: '#333366', color: '#fff' }}>
          <tr>
            {['Company', 'Contact', 'Email', 'Phone', 'Tax ID', 'Active'].map(h => (
              <th key={h} style={{ padding: '10px', textAlign: 'left' }}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {suppliers.map((s, i) => (
            <tr key={s.id} style={{ background: i % 2 === 0 ? '#f9f9f9' : '#fff' }}>
              <td style={{ padding: '8px 10px' }}>{s.companyName}</td>
              <td style={{ padding: '8px 10px' }}>{s.contactName}</td>
              <td style={{ padding: '8px 10px' }}>{s.email}</td>
              <td style={{ padding: '8px 10px' }}>{s.phone}</td>
              <td style={{ padding: '8px 10px' }}>{s.taxId}</td>
              <td style={{ padding: '8px 10px' }}>{s.active ? 'Yes' : 'No'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default SupplierList;
