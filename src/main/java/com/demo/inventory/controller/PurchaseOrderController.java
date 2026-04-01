package com.demo.inventory.controller;

import com.demo.inventory.dto.PurchaseOrderDTO;
import com.demo.inventory.model.PurchaseOrder;
import com.demo.inventory.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for purchase order management.
 * Exposes endpoints under /api/purchase-orders.
 */
@RestController
@RequestMapping("/purchase-orders")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderController {

    private final PurchaseOrderService orderService;

    public PurchaseOrderController(PurchaseOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    // TODO: add Javadoc
    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@Valid @RequestBody PurchaseOrderDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(dto));
    }

    // TODO: add Javadoc
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    // TODO: add Javadoc
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.findByStatus(status));
    }

    // TODO: add Javadoc
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrder>> findBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(orderService.findBySupplier(supplierId));
    }
}
