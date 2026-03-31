package com.demo.inventory.service;

import com.demo.inventory.dto.InventoryDTOs.SupplierDTO;
import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Supplier;
import com.demo.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service containing business logic for supplier management.
 */
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Returns all active suppliers.
     *
     * @return list of active suppliers
     */
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    /**
     * Finds a supplier by its ID.
     *
     * @param id supplier identifier
     * @return the found supplier
     * @throws ResourceNotFoundException if no supplier exists with the given id
     */
    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
    }

    // TODO: add Javadoc to this method
    public Supplier create(SupplierDTO dto) {
        if (supplierRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("A supplier with email '" + dto.getEmail() + "' already exists");
        }
        Supplier supplier = new Supplier();
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactName(dto.getContactName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setTaxId(dto.getTaxId());
        return supplierRepository.save(supplier);
    }

    // TODO: add Javadoc to this method
    public Supplier update(Long id, SupplierDTO dto) {
        Supplier supplier = findById(id);
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactName(dto.getContactName());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setTaxId(dto.getTaxId());
        return supplierRepository.save(supplier);
    }

    // TODO: add Javadoc to this method
    public void deactivate(Long id) {
        Supplier supplier = findById(id);
        supplier.setActive(false);
        supplierRepository.save(supplier);
    }

    // TODO: add Javadoc to this method
    public List<Supplier> search(String name) {
        return supplierRepository.findByCompanyNameContainingIgnoreCase(name);
    }
}
