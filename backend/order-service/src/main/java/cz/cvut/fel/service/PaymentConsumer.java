package cz.cvut.fel.service;

import cz.cvut.fel.event.PaymentEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private OrderStatusUpdateHandler handler;

    @KafkaListener(topics = "payment-topic", groupId = "payment-event-group")
    public void paymentEventConsumer(PaymentEvent paymentEvent) {
        handler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(),
                po -> po.setPaymentStatus(paymentEvent.getPaymentStatus()));
    }
}
