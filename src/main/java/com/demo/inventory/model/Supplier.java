package com.demo.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a supplier in the inventory system.
 * A supplier can provide multiple products through purchase orders.
 *
 * @author Carlos
 * @version 1.0
 */
@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Legal name of the supplier company.
     */
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String companyName;

    // TODO: add Javadoc to this field
    @NotBlank(message = "Contact name is required")
    @Size(max = 100)
    private String contactName;

    // TODO: add Javadoc to this field
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    // TODO: add Javadoc to this field
    @Size(max = 20)
    private String phone;

    // TODO: add Javadoc to this field
    @Size(max = 300)
    private String address;

    /**
     * Tax identification number.
     */
    @Size(max = 50)
    private String taxId;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
