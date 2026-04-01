package com.demo.inventory.controller;

import com.demo.inventory.dto.ProductDTO;
import com.demo.inventory.model.Product;
import com.demo.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for product management.
 * Exposes endpoints under /api/products.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products.
     *
     * @return list of products with status 200
     */
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Finds a product by ID.
     *
     * @param id product identifier
     * @return product with status 200, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // TODO: add Javadoc
    @GetMapping("/sku/{sku}")
    public ResponseEntity<Product> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.findBySku(sku));
    }

    // TODO: add Javadoc
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(dto));
    }

    // TODO: add Javadoc
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    // TODO: add Javadoc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        productService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: add Javadoc
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> findByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    // TODO: add Javadoc
    @GetMapping("/reorder-alert")
    public ResponseEntity<List<Product>> findBelowReorderLevel() {
        return ResponseEntity.ok(productService.findBelowReorderLevel());
    }

    // TODO: add Javadoc
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> findLowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(productService.findLowStock(threshold));
    }

    // TODO: add Javadoc
    @PatchMapping("/{id}/price")
    public ResponseEntity<Product> adjustPrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        return ResponseEntity.ok(productService.adjustPrice(id, newPrice));
    }
}
