package com.demo.inventory.service;

import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Product;
import com.demo.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setSku("SKU-001");
        sampleProduct.setName("Laptop Dell XPS");
        sampleProduct.setCategory("Electronics");
        sampleProduct.setUnitPrice(new BigDecimal("1299.99"));
        sampleProduct.setCurrentStock(50);
        sampleProduct.setReorderLevel(10);
        sampleProduct.setUnitOfMeasure("units");
        sampleProduct.setActive(true);
    }

    @Test
    @DisplayName("Should return all products")
    void findAll_shouldReturnProductList() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSku()).isEqualTo("SKU-001");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return product when ID exists")
    void findById_whenExists_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product result = productService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop Dell XPS");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
    void findById_whenNotExists_shouldThrowException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Should return products below reorder level")
    void findBelowReorderLevel_shouldReturnLowStockProducts() {
        when(productRepository.findProductsBelowReorderLevel()).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findBelowReorderLevel();

        assertThat(result).hasSize(1);
        verify(productRepository).findProductsBelowReorderLevel();
    }

    // TODO: add test for create() with duplicate SKU
    // TODO: add test for update() with SKU conflict
    // TODO: add test for findById() with null id (BUG #3)
    // TODO: add test for adjustPrice() with negative price (BUG #9)
    // TODO: add test for findLowStock() with null threshold (BUG #8)
}
