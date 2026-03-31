package com.demo.inventory.service;

import com.demo.inventory.dto.InventoryDTOs.StockMovementDTO;
import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Product;
import com.demo.inventory.model.StockMovement;
import com.demo.inventory.model.Warehouse;
import com.demo.inventory.repository.ProductRepository;
import com.demo.inventory.repository.StockMovementRepository;
import com.demo.inventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service containing business logic for stock movement management.
 * Every stock change must be recorded as a movement for full audit trail.
 *
 * NOTE FOR CURSOR PRACTICE:
 * This service contains intentional bugs. Use Cursor to identify and fix them.
 */
@Service
public class StockMovementService {

    // BUG #10: Field injection instead of constructor injection
    @Autowired
    private StockMovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    /**
     * Records a stock movement and updates the product's current stock.
     *
     * @param dto stock movement data
     * @return the persisted stock movement
     * @throws BusinessException if the movement would result in negative stock
     */
    @Transactional
    public StockMovement recordMovement(StockMovementDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + dto.getWarehouseId()));

        StockMovement.MovementType type;
        try {
            type = StockMovement.MovementType.valueOf(dto.getMovementType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid movement type: " + dto.getMovementType());
        }

        int newStock;
        if (type == StockMovement.MovementType.IN) {
            newStock = product.getCurrentStock() + dto.getQuantity();
        } else if (type == StockMovement.MovementType.OUT) {
            if (dto.getQuantity() > product.getCurrentStock()) {
                throw new BusinessException(
                    "Insufficient stock for product " + product.getSku() +
                    ". Available: " + product.getCurrentStock() +
                    ", Requested: " + dto.getQuantity());
            }
            newStock = product.getCurrentStock() - dto.getQuantity();
        } else {
            // ADJUSTMENT: quantity can be positive or negative
            // BUG #12: adjustment type always adds — subtraction is not supported
            newStock = product.getCurrentStock() + dto.getQuantity();
        }

        // BUG #13: does not validate that newStock does not go negative
        product.setCurrentStock(newStock);
        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouse(warehouse);
        movement.setMovementType(type);
        movement.setQuantity(dto.getQuantity());
        movement.setStockAfterMovement(newStock);
        movement.setReason(dto.getReason());
        movement.setReferenceDocument(dto.getReferenceDocument());

        return movementRepository.save(movement);
    }

    /**
     * Returns all movements for a specific product.
     *
     * @param productId product identifier
     * @return list of stock movements ordered by date
     */
    public List<StockMovement> findByProduct(Long productId) {
        // BUG #14: does not verify the product exists before querying
        return movementRepository.findByProductId(productId);
    }

    // TODO: add Javadoc to this method
    public List<StockMovement> findByWarehouse(Long warehouseId) {
        return movementRepository.findByWarehouseId(warehouseId);
    }

    // TODO: add Javadoc to this method
    public List<StockMovement> findAll() {
        return movementRepository.findAll();
    }
}
