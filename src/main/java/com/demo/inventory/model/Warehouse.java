package com.demo.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a physical warehouse location.
 * Products are stored and tracked per warehouse.
 *
 * @author Carlos
 * @version 1.0
 */
@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique code identifying the warehouse.
     */
    @NotBlank(message = "Warehouse code is required")
    @Size(min = 2, max = 20)
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * Human-readable warehouse name.
     */
    @NotBlank(message = "Warehouse name is required")
    @Size(min = 2, max = 150)
    @Column(nullable = false)
    private String name;

    // TODO: add Javadoc to this field
    @NotBlank(message = "Location is required")
    @Size(max = 300)
    private String location;

    // TODO: add Javadoc to this field
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;

    // TODO: add Javadoc to this field
    @Min(0)
    @Column(nullable = false)
    private Integer currentOccupancy;

    // TODO: add Javadoc to this field
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
        if (currentOccupancy == null) currentOccupancy = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
