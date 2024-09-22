package cz.cvut.fel.repository;

import cz.cvut.fel.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link CartItem} entities.
 *
 * Provides methods for performing CRUD operations on cart items in the database,
 * as well as custom queries for finding and deleting items by cart ID.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Deletes all cart items associated with the specified cart ID.
     *
     * @param id the ID of the cart whose items should be deleted.
     */
    void deleteByCartId(Long id);

    /**
     * Finds all cart items associated with the specified cart ID.
     *
     * @param id the ID of the cart whose items should be retrieved.
     * @return a list of {@link CartItem} objects.
     */
    List<CartItem> findByCartId(Long id);
}
