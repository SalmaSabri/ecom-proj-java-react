package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderStatusPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderEvent(OrderDto orderDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderDto, orderStatus);
        kafkaTemplate.send("order-topic", orderEvent);
    }
}
