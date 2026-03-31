package com.demo.inventory.repository;

import com.demo.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link Product} entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByActiveTrueAndCurrentStockLessThanEqual(Integer threshold);

    List<Product> findByNameContainingIgnoreCase(String name);

    // TODO: add Javadoc
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.currentStock <= p.reorderLevel")
    List<Product> findProductsBelowReorderLevel();

    // TODO: add Javadoc
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category AND p.active = true")
    Long countByCategory(@Param("category") String category);
}
