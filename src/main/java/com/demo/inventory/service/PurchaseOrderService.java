package com.demo.inventory.service;

import com.demo.inventory.dto.InventoryDTOs.PurchaseOrderDTO;
import com.demo.inventory.exception.BusinessException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.*;
import com.demo.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service containing business logic for purchase order management.
 * Handles the full lifecycle: DRAFT → SUBMITTED → APPROVED → RECEIVED → CANCELLED.
 *
 * NOTE FOR CURSOR PRACTICE:
 * This service contains intentional bugs. Use Cursor to identify and fix them.
 */
@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderService(PurchaseOrderRepository orderRepository,
                                 SupplierRepository supplierRepository,
                                 ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    /**
     * Returns all purchase orders in the system.
     *
     * @return list of all purchase orders
     */
    public List<PurchaseOrder> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Finds a purchase order by its ID.
     *
     * @param id purchase order identifier
     * @return the found purchase order
     * @throws ResourceNotFoundException if not found
     */
    public PurchaseOrder findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id));
    }

    // TODO: add Javadoc to this method
    @Transactional
    public PurchaseOrder create(PurchaseOrderDTO dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));

        PurchaseOrder order = new PurchaseOrder();
        order.setOrderNumber(generateOrderNumber());
        order.setSupplier(supplier);
        order.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        order.setNotes(dto.getNotes());

        List<PurchaseOrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setPurchaseOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setUnitPrice(itemDto.getUnitPrice());
            // BUG #15: lineTotal is not set here — relies on @PrePersist which is buggy
            items.add(item);
            total = total.add(itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        order.setItems(items);
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    // TODO: add Javadoc to this method
    @Transactional
    public PurchaseOrder updateStatus(Long id, String newStatus) {
        PurchaseOrder order = findById(id);
        PurchaseOrder.Status status;
        try {
            status = PurchaseOrder.Status.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid status: " + newStatus);
        }

        // BUG #16: no validation of allowed status transitions
        // e.g. CANCELLED → APPROVED should not be allowed
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // TODO: add Javadoc to this method
    public List<PurchaseOrder> findByStatus(String status) {
        // BUG #17: no validation that status is a valid enum value
        return orderRepository.findByStatus(PurchaseOrder.Status.valueOf(status.toUpperCase()));
    }

    // TODO: add Javadoc to this method
    public List<PurchaseOrder> findBySupplier(Long supplierId) {
        // BUG #18: does not verify the supplier exists before querying
        return orderRepository.findBySupplierId(supplierId);
    }

    private String generateOrderNumber() {
        return "PO-" + DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
    }
}
