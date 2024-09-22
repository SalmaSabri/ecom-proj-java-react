package cz.cvut.fel.service;

import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.kafka.OrderStatusPublisher;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.OrderRepository;
import cz.cvut.fel.status.OrderStatus;
import cz.cvut.fel.status.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Component responsible for handling updates to order statuses.
 *
 * This service updates the status of an order based on changes to the payment status
 * and publishes appropriate events to Kafka. It processes and transitions the order
 * to either "COMPLETED" or "CANCELLED" depending on whether the payment is successful.
 */
@Component
@AllArgsConstructor
public class OrderStatusUpdateHandler {

    private OrderRepository orderRepository;

    private OrderStatusPublisher orderStatusPublisher;

    /**
     * Updates an order based on the provided consumer operation.
     *
     * This method applies the provided consumer to modify the order, then updates
     * the order status based on the new payment status and publishes the updated status.
     *
     * @param id the ID of the order to be updated.
     * @param consumer the consumer operation that modifies the order.
     */
    @Transactional
    public void updateOrder(Long id, Consumer<PurchaseOrder> consumer) {
        orderRepository.findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));
    }

    /**
     * Updates the order status and publishes events based on the current payment status.
     *
     * If the payment is complete, the order status is set to "COMPLETED". Otherwise, it is
     * set to "CANCELLED". It then publishes history events to Kafka and, if the payment failed,
     * also publishes an order status update event.
     *
     * @param purchaseOrder the {@link PurchaseOrder} entity being updated.
     */
    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        orderStatusPublisher.publishHistoryEvent(Mapper.convertOrderEntityToDto(purchaseOrder));
        if (!isPaymentComplete) {
            orderStatusPublisher.publishOrderEvent(Mapper.convertOrderEntityToDto(purchaseOrder), orderStatus);
        }
    }
}
