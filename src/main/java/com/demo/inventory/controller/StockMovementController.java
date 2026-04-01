package com.demo.inventory.controller;

import com.demo.inventory.dto.StockMovementDTO;
import com.demo.inventory.model.StockMovement;
import com.demo.inventory.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for stock movement management.
 * Exposes endpoints under /api/stock-movements.
 */
@RestController
@RequestMapping("/stock-movements")
@CrossOrigin(origins = "http://localhost:3000")
public class StockMovementController {

    private final StockMovementService movementService;

    public StockMovementController(StockMovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public ResponseEntity<List<StockMovement>> findAll() {
        return ResponseEntity.ok(movementService.findAll());
    }

    // TODO: add Javadoc
    @PostMapping
    public ResponseEntity<StockMovement> recordMovement(@Valid @RequestBody StockMovementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.recordMovement(dto));
    }

    // TODO: add Javadoc
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovement>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(movementService.findByProduct(productId));
    }

    // TODO: add Javadoc
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockMovement>> findByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(movementService.findByWarehouse(warehouseId));
    }
}
