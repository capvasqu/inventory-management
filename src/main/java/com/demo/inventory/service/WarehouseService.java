package com.demo.inventory.service;

import com.demo.inventory.dto.WarehouseDTO;
import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Warehouse;
import com.demo.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service containing business logic for warehouse management.
 */
@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Returns all warehouses in the system.
     *
     * @return list of all warehouses
     */
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    /**
     * Finds a warehouse by its ID.
     *
     * @param id warehouse identifier
     * @return the found warehouse
     * @throws ResourceNotFoundException if no warehouse exists with the given id
     */
    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }

    // TODO: add Javadoc to this method
    public Warehouse create(WarehouseDTO dto) {
        if (warehouseRepository.existsByCode(dto.getCode())) {
            throw new BusinessException("A warehouse with code '" + dto.getCode() + "' already exists");
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(dto.getCode());
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        return warehouseRepository.save(warehouse);
    }

    // TODO: add Javadoc to this method
    public Warehouse update(Long id, WarehouseDTO dto) {
        Warehouse warehouse = findById(id);
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        return warehouseRepository.save(warehouse);
    }

    // TODO: add Javadoc to this method
    public void deactivate(Long id) {
        Warehouse warehouse = findById(id);
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
    }

    // TODO: add Javadoc to this method
    public List<Warehouse> findWithAvailableCapacity() {
        return warehouseRepository.findWarehousesWithAvailableCapacity();
    }
}
