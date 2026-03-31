package com.demo.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product in the inventory system.
 * A product can be stored across multiple warehouses and
 * supplied by multiple suppliers.
 *
 * @author Carlos
 * @version 1.0
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique Stock Keeping Unit identifier.
     */
    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String sku;

    /**
     * Product display name.
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150)
    @Column(nullable = false)
    private String name;

    // TODO: add Javadoc to this field
    @Size(max = 500)
    private String description;

    // TODO: add Javadoc to this field
    @NotBlank(message = "Category is required")
    private String category;

    // TODO: add Javadoc to this field
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    // TODO: add Javadoc to this field
    @Min(value = 0, message = "Current stock cannot be negative")
    @Column(nullable = false)
    private Integer currentStock;

    /**
     * Minimum stock level before reorder is triggered.
     */
    @Min(0)
    @Column(nullable = false)
    private Integer reorderLevel;

    /**
     * Unit of measure (e.g. units, kg, liters).
     */
    @NotBlank(message = "Unit of measure is required")
    private String unitOfMeasure;

    // TODO: add Javadoc to this field
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // TODO: add Javadoc to this field
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
        if (currentStock == null) currentStock = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
