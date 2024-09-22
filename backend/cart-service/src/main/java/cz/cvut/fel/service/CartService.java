package cz.cvut.fel.service;

import cz.cvut.fel.dto.CartDto;
import cz.cvut.fel.event.CartEvent;
import jakarta.transaction.Transactional;

/**
 * Service interface for managing cart-related operations.
 *
 * Provides methods for adding products to a cart, submitting a cart for checkout, and
 * retrieving a user's cart details.
 */
public interface CartService {

    /**
     * Adds a product to the user's cart by sending a cart event to the appropriate service.
     *
     * @param cartEvent the event containing details of the product to be added.
     */
    @Transactional
    void addProductToCart(CartEvent cartEvent);

    /**
     * Submits the user's cart for order creation and checkout.
     *
     * This method fetches the user's cart, calculates the total price, and calls the order
     * service to create an order.
     *
     * @param userId the ID of the user submitting the cart.
     */
    @Transactional
    void submitCart(String userId);

    /**
     * Retrieves the cart details for the authenticated user.
     *
     * This method fetches the user's cart and its items and returns them in a {@link CartDto}.
     *
     * @param userId the ID of the user whose cart is being retrieved.
     * @return a {@link CartDto} containing the cart details.
     */
    CartDto getUserCart(String userId);
}
