package cz.cvut.fel.kafka;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.event.HistoryEvent;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * A Kafka publisher component for sending order and history events.
 *
 * This class is responsible for publishing events related to orders and order history to
 * specific Kafka topics. It uses two different Kafka templates to send events to the
 * "order-topic" and "history-topic".
 */
@Component
@AllArgsConstructor
public class OrderStatusPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplateOrder;
    private final KafkaTemplate<String, HistoryEvent> kafkaTemplateHistory;

    /**
     * Publishes an order event to the "order-topic".
     *
     * This method creates an {@link OrderEvent} based on the provided {@link OrderDto} and
     * {@link OrderStatus}, and sends the event to the Kafka "order-topic".
     *
     * @param orderDto the order data to be included in the event.
     * @param orderStatus the current status of the order.
     */
    public void publishOrderEvent(OrderDto orderDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderDto, orderStatus);
        kafkaTemplateOrder.send("order-topic", orderEvent);
    }

    /**
     * Publishes a history event to the "history-topic".
     *
     * This method creates a {@link HistoryEvent} based on the provided {@link OrderDto},
     * including the user ID and order ID, and sends it to the Kafka "history-topic".
     *
     * @param orderDto the order data used to create the history event.
     */
    public void publishHistoryEvent(OrderDto orderDto){
        HistoryEvent historyEvent = HistoryEvent.builder()
                        .userId(orderDto.getUserId())
                        .orderId(orderDto.getOrderId())
                        .build();
        kafkaTemplateHistory.send("history-topic", historyEvent);
    }
}
