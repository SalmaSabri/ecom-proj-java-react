package cz.cvut.fel.repository;

import cz.cvut.fel.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Cart} entities.
 *
 * Provides methods for performing CRUD operations on carts in the database,
 * as well as a custom query for finding a cart by user ID.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Finds a cart associated with the specified user ID.
     *
     * @param userId the ID of the user whose cart should be retrieved.
     * @return an {@link Optional} containing the cart, if found.
     */
    Optional<Cart> findByUserId(String userId);
}
