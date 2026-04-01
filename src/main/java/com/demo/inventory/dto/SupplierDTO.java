package com.demo.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SupplierDTO {
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
