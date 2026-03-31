package com.demo.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity tracking every stock movement (IN, OUT, ADJUSTMENT) for a product
 * in a specific warehouse. Provides a full audit trail of inventory changes.
 *
 * @author Carlos
 * @version 1.0
 */
@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    /**
     * Type of stock movement.
     */
    public enum MovementType {
        IN,          // Stock received (purchase order, return)
        OUT,         // Stock dispatched (sale, transfer)
        ADJUSTMENT   // Manual correction (inventory count)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product affected by this movement.
     */
    @NotNull(message = "Product is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Warehouse where the movement occurred.
     */
    @NotNull(message = "Warehouse is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // TODO: add Javadoc to this field
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    // TODO: add Javadoc to this field
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Stock level of the product in this warehouse after the movement.
     */
    @Column(nullable = false)
    private Integer stockAfterMovement;

    // TODO: add Javadoc to this field
    @Size(max = 300)
    private String reason;

    /**
     * Reference to related document (e.g. purchase order number, sale ID).
     */
    @Size(max = 100)
    private String referenceDocument;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
