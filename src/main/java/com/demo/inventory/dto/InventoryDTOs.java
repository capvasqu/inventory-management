package com.demo.inventory.dto;

import com.demo.inventory.model.PurchaseOrder;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for creating or updating a Product.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class ProductDTO {
    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50)
    private String sku;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal unitPrice;

    @Min(0)
    private Integer reorderLevel;

    @NotBlank(message = "Unit of measure is required")
    private String unitOfMeasure;
}

/**
 * DTO for creating or updating a Supplier.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class SupplierDTO {
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200)
    private String companyName;

    @NotBlank(message = "Contact name is required")
    @Size(max = 100)
    private String contactName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 300)
    private String address;

    @Size(max = 50)
    private String taxId;
}

/**
 * DTO for creating or updating a Warehouse.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class WarehouseDTO {
    @NotBlank(message = "Warehouse code is required")
    @Size(min = 2, max = 20)
    private String code;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150)
    private String name;

    @NotBlank(message = "Location is required")
    @Size(max = 300)
    private String location;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}

/**
 * DTO for creating a Purchase Order.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class PurchaseOrderDTO {
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    private LocalDate expectedDeliveryDate;

    @Size(max = 500)
    private String notes;

    @NotNull
    private List<PurchaseOrderItemDTO> items;
}

/**
 * DTO for a line item within a purchase order.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class PurchaseOrderItemDTO {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Min(value = 1)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal unitPrice;
}

/**
 * DTO for recording a stock movement.
 */
@Data @NoArgsConstructor @AllArgsConstructor
class StockMovementDTO {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Movement type is required")
    private String movementType;

    @Min(value = 1)
    private Integer quantity;

    @Size(max = 300)
    private String reason;

    @Size(max = 100)
    private String referenceDocument;
}
