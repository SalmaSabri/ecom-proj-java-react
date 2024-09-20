package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.OrderedItem;
import cz.cvut.fel.event.HistoryEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.OrderedItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderListener {

    private OrderStatusUpdateHandler handler;
    private OrderedItemRepository orderedItemRepository;

    @KafkaListener(topics = "payment-topic", groupId = "payment-event-group")
    public void paymentEventConsumer(PaymentEvent paymentEvent) {
        handler.updateOrder(paymentEvent.getPaymentDto().getOrderId(),
                po -> po.setPaymentStatus(paymentEvent.getPaymentStatus()));
    }

    @KafkaListener(topics = "history-topic", groupId = "history-event-group")
    public void historyEventConsumer(HistoryEvent historyEventWithOrderedItems) {
        List<OrderItemDto> orderItemDtos = historyEventWithOrderedItems.getOrderItemDtos();

        // Convert OrderItemDto to OrderItemEntity before saving
        List<OrderedItem> orderedItems = orderItemDtos.stream()
                .map(Mapper::toEntity)
                .toList();

        // Save all the order items in batch
        orderedItemRepository.saveAll(orderedItems);
    }
}
