package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderDto;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for managing orders.
 *
 * Provides methods for creating and retrieving orders.
 */
public interface OrderService {

    /**
     * Creates a new order.
     *
     * This method saves the order details to the database and publishes an event to indicate
     * that the order has been created.
     *
     * @param orderDto the details of the order to be created.
     * @return the created {@link OrderDto}.
     */
    @Transactional
    OrderDto createOrder(OrderDto orderDto);

    /**
     * Retrieves all orders for a specific user.
     *
     * This method fetches all orders associated with the given user ID.
     *
     * @param userId the ID of the user whose orders are to be fetched.
     * @return a list of {@link OrderDto} containing the user's orders.
     */
    List<OrderDto> getAllOrders(String userId);
}
