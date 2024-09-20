package cz.cvut.fel.service;

import cz.cvut.fel.event.CartEventDto;
import cz.cvut.fel.event.HistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartProducer {
    private final KafkaTemplate<String, CartEventDto> kafkaTemplate;

    private final KafkaTemplate<String, HistoryEvent> kafkaTemplateHistory;

    public void sendCartEvent(CartEventDto event) {
        kafkaTemplate.send("cart-topic", event);
    }

    public void publishHistoryEvent(HistoryEvent historyEventWithOrderedItems) {
        kafkaTemplateHistory.send("history-topic", historyEventWithOrderedItems);
    }
}
