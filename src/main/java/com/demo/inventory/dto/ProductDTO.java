package com.demo.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {
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
