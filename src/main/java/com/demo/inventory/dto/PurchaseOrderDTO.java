package com.demo.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class PurchaseOrderDTO {
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    private LocalDate expectedDeliveryDate;

    @Size(max = 500)
    private String notes;

    @NotNull
    private List<PurchaseOrderItemDTO> items;
}
