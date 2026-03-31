package com.demo.inventory.repository;

import com.demo.inventory.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link Warehouse} entity.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByCode(String code);
    boolean existsByCode(String code);
    List<Warehouse> findByActiveTrue();

    // TODO: add Javadoc
    @Query("SELECT w FROM Warehouse w WHERE w.active = true AND w.currentOccupancy < w.capacity")
    List<Warehouse> findWarehousesWithAvailableCapacity();
}
