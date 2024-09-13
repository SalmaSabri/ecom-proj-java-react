package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderRequestDto;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderStatusPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
        kafkaTemplate.send("order-topic", orderEvent);
    }
}
