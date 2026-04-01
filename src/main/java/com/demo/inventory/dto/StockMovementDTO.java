package com.demo.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class StockMovementDTO {
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
