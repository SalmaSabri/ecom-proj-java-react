package cz.cvut.fel.repository;

import cz.cvut.fel.entity.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link OrderedItem} entities.
 *
 * This repository provides CRUD operations and custom queries for the {@link OrderedItem} entity.
 */
@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
    /**
     * Finds all ordered items associated with the given order ID.
     *
     * @param id the ID of the order whose items are being fetched.
     * @return a list of {@link OrderedItem} entities that belong to the specified order.
     */
    List<OrderedItem> findByOrderId(Long id);
}
