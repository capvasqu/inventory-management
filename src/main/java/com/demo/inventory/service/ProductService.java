package com.demo.inventory.service;

import com.demo.inventory.dto.ProductDTO;
import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Product;
import com.demo.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service containing business logic for product management.
 *
 * NOTE FOR CURSOR PRACTICE:
 * This service contains intentional bugs and improvement opportunities.
 * Use Cursor to identify and fix them.
 */
@Service
public class ProductService {

    // BUG #2: Field injection instead of constructor injection
    @Autowired
    private ProductRepository productRepository;

    /**
     * Returns all active products in the system.
     *
     * @return list of active products
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds a product by its ID.
     *
     * @param id product identifier
     * @return the found product
     * @throws ResourceNotFoundException if no product exists with the given id
     */
    public Product findById(Long id) {
        // BUG #3: does not validate that id is not null or negative
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    /**
     * Finds a product by its SKU.
     *
     * @param sku stock keeping unit identifier
     * @return the found product
     */
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
    }

    // TODO: add Javadoc to this method
    public Product create(ProductDTO dto) {
        // BUG #4: does not check for duplicate SKU before saving
        Product product = new Product();
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setUnitPrice(dto.getUnitPrice());
        product.setReorderLevel(dto.getReorderLevel() != null ? dto.getReorderLevel() : 0);
        product.setUnitOfMeasure(dto.getUnitOfMeasure());
        return productRepository.save(product);
    }

    // TODO: add Javadoc to this method
    public Product update(Long id, ProductDTO dto) {
        Product product = findById(id);
        // BUG #5: SKU update is allowed even if another product already has it
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setUnitPrice(dto.getUnitPrice());
        product.setReorderLevel(dto.getReorderLevel());
        product.setUnitOfMeasure(dto.getUnitOfMeasure());
        return productRepository.save(product);
    }

    // TODO: add Javadoc to this method
    public void deactivate(Long id) {
        // BUG #6: physically deletes instead of soft delete (active = false)
        productRepository.deleteById(id);
    }

    // TODO: add Javadoc to this method
    public List<Product> findByCategory(String category) {
        // BUG #7: does not validate that category is not null or empty
        return productRepository.findByCategoryIgnoreCase(category);
    }

    /**
     * Returns all products whose current stock is at or below the reorder level.
     *
     * @return list of products needing reorder
     */
    public List<Product> findBelowReorderLevel() {
        return productRepository.findProductsBelowReorderLevel();
    }

    // TODO: add Javadoc to this method
    public List<Product> findLowStock(Integer threshold) {
        // BUG #8: no validation that threshold is not null or negative
        return productRepository.findByActiveTrueAndCurrentStockLessThanEqual(threshold);
    }

    // TODO: add Javadoc to this method
    public Product adjustPrice(Long id, BigDecimal newPrice) {
        // BUG #9: does not validate that newPrice is greater than zero
        Product product = findById(id);
        product.setUnitPrice(newPrice);
        return productRepository.save(product);
    }
}
