package cz.cvut.fel.kafka;

import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.service.PaymentService;
import cz.cvut.fel.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Kafka listener for processing order events and handling payments.
 *
 * This listener listens to events from the "order-topic" and processes payments based on the order status.
 * If the payment is successful, a payment event is sent to the "payment-topic".
 */
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentService paymentService;

    /**
     * Listens to order events from the "order-topic" and processes the payment accordingly.
     *
     * If the order is created successfully, it checks the user's balance and either completes the payment
     * or cancels the order. The result is sent to the "payment-topic".
     *
     * @param orderEvent the {@link OrderEvent} containing the order details.
     * @return the {@link PaymentEvent} indicating the payment status, which is forwarded to the "payment-topic".
     */
    @KafkaListener(topics = "order-topic", groupId = "order-event-group")
    @SendTo(value = "payment-topic")
    public PaymentEvent handleOrderEvent(OrderEvent orderEvent) {
        return processPayment(orderEvent);
    }

    /**
     * Processes the payment based on the order status.
     *
     * If the order status is "ORDER_CREATED", it checks the balance and completes the payment if possible.
     * If the balance is insufficient, the order is canceled. If the order is being canceled, it updates the balance.
     *
     * @param orderEvent the {@link OrderEvent} containing the order details.
     * @return the {@link PaymentEvent} indicating the payment status.
     */
    private PaymentEvent processPayment(OrderEvent orderEvent) {
        // get the user id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
            return paymentService.newOrderEvent(orderEvent);
        } else {
            paymentService.cancelOrderEvent(orderEvent);
            return null;
        }
    }
}
