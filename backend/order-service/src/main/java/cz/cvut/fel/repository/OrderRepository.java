package cz.cvut.fel.repository;

import cz.cvut.fel.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link PurchaseOrder} entities.
 *
 * This repository provides CRUD operations and custom queries for the {@link PurchaseOrder} entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder,Long> {
    /**
     * Finds all purchase orders associated with a specific user ID.
     *
     * @param userId the ID of the user whose orders are being fetched.
     * @return a list of {@link PurchaseOrder} entities that belong to the specified user.
     */
    List<PurchaseOrder> findByUserId(String userId);
}
