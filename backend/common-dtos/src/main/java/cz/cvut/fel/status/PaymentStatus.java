package cz.cvut.fel.status;

/**
 * Enum representing the different statuses of a payment.
 *
 * A payment can be in one of the following states:
 * <ul>
 *   <li>{@link #PAYMENT_COMPLETED} - The payment has been successfully completed.</li>
 *   <li>{@link #PAYMENT_FAILED} - The payment has failed.</li>
 * </ul>
 */
public enum PaymentStatus {

    PAYMENT_COMPLETED,PAYMENT_FAILED
}
