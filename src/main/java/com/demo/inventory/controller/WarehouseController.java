package com.demo.inventory.controller;

import com.demo.inventory.dto.WarehouseDTO;
import com.demo.inventory.model.Warehouse;
import com.demo.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for warehouse management.
 * Exposes endpoints under /api/warehouses.
 */
@RestController
@RequestMapping("/warehouses")
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> findAll() {
        return ResponseEntity.ok(warehouseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.findById(id));
    }

    // TODO: add Javadoc
    @PostMapping
    public ResponseEntity<Warehouse> create(@Valid @RequestBody WarehouseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.create(dto));
    }

    // TODO: add Javadoc
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> update(@PathVariable Long id, @Valid @RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(warehouseService.update(id, dto));
    }

    // TODO: add Javadoc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        warehouseService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: add Javadoc
    @GetMapping("/available")
    public ResponseEntity<List<Warehouse>> findWithAvailableCapacity() {
        return ResponseEntity.ok(warehouseService.findWithAvailableCapacity());
    }
}
