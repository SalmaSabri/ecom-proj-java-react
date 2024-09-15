package cz.cvut.fel.service;

import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "order-event-group")
    @SendTo(value = "payment-topic")
    public PaymentEvent handleOrderEvent(OrderEvent orderEvent) {
        return processPayment(orderEvent);
    }

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
