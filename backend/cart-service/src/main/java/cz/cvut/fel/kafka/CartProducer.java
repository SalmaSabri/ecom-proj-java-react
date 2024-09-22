package cz.cvut.fel.kafka;

import cz.cvut.fel.event.CartEvent;
import cz.cvut.fel.event.HistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartProducer {
    private final KafkaTemplate<String, CartEvent> kafkaTemplateCart;
    private final KafkaTemplate<String, HistoryEvent> kafkaTemplateHistory;

    public void publishCartEvent(CartEvent event) {
        kafkaTemplateCart.send("cart-topic", event);
    }

    public void publishHistoryEvent(HistoryEvent historyEventWithOrderedItems) {
        kafkaTemplateHistory.send("history-topic", historyEventWithOrderedItems);
    }
}
