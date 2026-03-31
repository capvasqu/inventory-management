package com.demo.inventory.service;

import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.model.*;
import com.demo.inventory.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StockMovementService Tests")
class StockMovementServiceTest {

    @Mock
    private StockMovementRepository movementRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private StockMovementService movementService;

    private Product sampleProduct;
    private Warehouse sampleWarehouse;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setSku("SKU-001");
        sampleProduct.setName("Laptop Dell XPS");
        sampleProduct.setCurrentStock(50);
        sampleProduct.setActive(true);

        sampleWarehouse = new Warehouse();
        sampleWarehouse.setId(1L);
        sampleWarehouse.setCode("WH-01");
        sampleWarehouse.setName("Main Warehouse");
        sampleWarehouse.setCapacity(1000);
        sampleWarehouse.setCurrentOccupancy(200);
        sampleWarehouse.setActive(true);
    }

    @Test
    @DisplayName("Should increase stock when recording an IN movement")
    void recordMovement_inType_shouldIncreaseStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(sampleWarehouse));
        when(productRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(movementRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var dto = new com.demo.inventory.dto.InventoryDTOs.StockMovementDTO(
                1L, 1L, "IN", 20, "Purchase received", "PO-001");

        StockMovement result = movementService.recordMovement(dto);

        assertThat(result.getStockAfterMovement()).isEqualTo(70);
        assertThat(sampleProduct.getCurrentStock()).isEqualTo(70);
    }

    @Test
    @DisplayName("Should throw exception for invalid movement type")
    void recordMovement_invalidType_shouldThrowBusinessException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(sampleWarehouse));

        var dto = new com.demo.inventory.dto.InventoryDTOs.StockMovementDTO(
                1L, 1L, "INVALID", 10, "Test", null);

        assertThatThrownBy(() -> movementService.recordMovement(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid movement type");
    }

    // TODO: add test for OUT movement with insufficient stock (BUG #11)
    // TODO: add test for ADJUSTMENT movement with negative quantity (BUG #12)
    // TODO: add test for OUT movement that results in negative stock (BUG #13)
    // TODO: add test for findByProduct() with non-existent product (BUG #14)
}
