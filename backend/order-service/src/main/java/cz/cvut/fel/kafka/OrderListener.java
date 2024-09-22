package cz.cvut.fel.kafka;

import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.OrderedItem;
import cz.cvut.fel.event.HistoryEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.OrderedItemRepository;
import cz.cvut.fel.service.OrderStatusUpdateHandler;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kafka listener for consuming events related to orders and payments.
 *
 * This component listens to messages from Kafka topics related to payment events and order history events.
 * It processes the events and performs updates in the system accordingly.
 */
@Component
@AllArgsConstructor
public class OrderListener {

    private OrderStatusUpdateHandler handler;
    private OrderedItemRepository orderedItemRepository;

    /**
     * Consumes payment events from the "payment-topic".
     *
     * When a payment event is received, the corresponding order's payment status is updated
     * based on the details in the {@link PaymentEvent}.
     *
     * @param paymentEvent the {@link PaymentEvent} containing payment information.
     */
    @KafkaListener(topics = "payment-topic", groupId = "payment-event-group")
    public void paymentEventConsumer(PaymentEvent paymentEvent) {
        handler.updateOrder(paymentEvent.getPaymentDto().getOrderId(),
                po -> po.setPaymentStatus(paymentEvent.getPaymentStatus()));
    }

    /**
     * Consumes history events from the "history-topic".
     *
     * When an order history event is received, the ordered items are converted from DTOs to entities
     * and saved to the database in batch.
     *
     * @param historyEventWithOrderedItems the {@link HistoryEvent} containing ordered item details.
     */
    @KafkaListener(topics = "history-topic", groupId = "history-event-group")
    public void historyEventConsumer(HistoryEvent historyEventWithOrderedItems) {
        List<OrderItemDto> orderItemDtos = historyEventWithOrderedItems.getOrderItemDtos();

        // Convert OrderItemDto to OrderItemEntity before saving
        List<OrderedItem> orderedItems = orderItemDtos.stream()
                .map(Mapper::convertDtoToOrderItemEntity)
                .toList();

        // Save all the order items in batch
        orderedItemRepository.saveAll(orderedItems);
    }
}
