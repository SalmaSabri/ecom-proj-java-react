package cz.cvut.fel.status;

/**
 * Enum representing the different statuses of an order.
 *
 * An order can be in one of the following states:
 * <ul>
 *   <li>{@link #ORDER_CREATED} - The order has been created but not yet completed.</li>
 *   <li>{@link #ORDER_COMPLETED} - The order has been successfully completed.</li>
 *   <li>{@link #ORDER_CANCELLED} - The order has been cancelled.</li>
 * </ul>
 */
public enum OrderStatus {

    ORDER_CREATED,ORDER_COMPLETED,ORDER_CANCELLED
}
