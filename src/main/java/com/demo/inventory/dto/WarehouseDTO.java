package com.demo.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class WarehouseDTO {
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
