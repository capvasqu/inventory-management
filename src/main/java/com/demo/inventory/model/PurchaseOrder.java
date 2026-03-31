package com.demo.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a purchase order placed to a supplier.
 * Contains one or more line items and tracks fulfillment status.
 *
 * @author Carlos
 * @version 1.0
 */
@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    /**
     * Possible statuses for a purchase order lifecycle.
     */
    public enum Status {
        DRAFT, SUBMITTED, APPROVED, RECEIVED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Auto-generated order reference number.
     */
    @Column(nullable = false, unique = true)
    private String orderNumber;

    /**
     * Supplier fulfilling this order.
     */
    @NotNull(message = "Supplier is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // TODO: add Javadoc to this field
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // TODO: add Javadoc to this field
    private LocalDate expectedDeliveryDate;

    // TODO: add Javadoc to this field
    @Column(precision = 14, scale = 2)
    private BigDecimal totalAmount;

    // TODO: add Javadoc to this field
    @Size(max = 500)
    private String notes;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = Status.DRAFT;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
