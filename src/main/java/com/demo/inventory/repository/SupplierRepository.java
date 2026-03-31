package com.demo.inventory.repository;

import com.demo.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link Supplier} entity.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Supplier> findByActiveTrue();
    List<Supplier> findByCompanyNameContainingIgnoreCase(String name);
}
