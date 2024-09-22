package cz.cvut.fel.service;

import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.event.PaymentEvent;

/**
 * Service interface for managing payment-related operations.
 *
 * Provides methods for processing new orders, canceling orders, managing user balances, and handling payments.
 */
public interface PaymentService {

    /**
     * Processes a new order event and handles payment.
     *
     * If the user's balance is sufficient, the payment is completed, and the balance is deducted.
     * Otherwise, the payment is marked as failed.
     *
     * @param orderEvent the {@link OrderEvent} containing the order details.
     * @return a {@link PaymentEvent} indicating whether the payment was completed or failed.
     */
    PaymentEvent newOrderEvent(OrderEvent orderEvent);

    /**
     * Cancels an order event and restores the user's balance.
     *
     * This method deletes the user transaction for the order and restores the amount to the user's balance.
     *
     * @param orderEvent the {@link OrderEvent} representing the canceled order.
     */
    void cancelOrderEvent(OrderEvent orderEvent);

    /**
     * Creates a new user balance.
     *
     * This method initializes the user's balance. If no balance amount is provided, it defaults to zero.
     *
     * @param userBalanceDto the {@link UserBalanceDto} containing the balance details.
     * @return the created {@link UserBalanceDto}.
     */
    UserBalanceDto createBalance(UserBalanceDto userBalanceDto);

    /**
     * Adds funds to an existing user's balance.
     *
     * If the user's balance exists, the provided amount is added to the balance.
     *
     * @param userBalanceDto the {@link UserBalanceDto} containing the top-up amount.
     * @return the updated {@link UserBalanceDto} after the top-up.
     */
    UserBalanceDto topUpBalance(UserBalanceDto userBalanceDto);

    /**
     * Retrieves the balance for a specific user.
     *
     * This method fetches the user's current balance based on their user ID.
     *
     * @param userId the ID of the user whose balance is being retrieved.
     * @return the {@link UserBalanceDto} representing the user's balance.
     */
    UserBalanceDto getBalance(String userId);
}
