package cz.cvut.fel.service.impl;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.OrderedItem;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.kafka.OrderStatusPublisher;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.OrderRepository;
import cz.cvut.fel.repository.OrderedItemRepository;
import cz.cvut.fel.service.OrderService;
import cz.cvut.fel.status.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        PurchaseOrder order = orderRepository.save(Mapper.convertDtoToOrderEntity(orderDto));
        orderDto.setOrderId(order.getId());
        //produce kafka event with status ORDER_CREATED
        orderStatusPublisher.publishOrderEvent(orderDto, OrderStatus.ORDER_CREATED);
        return Mapper.convertOrderEntityToDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(String userId){
        List<PurchaseOrder> orders = orderRepository.findByUserId(userId);

        // For each order, fetch its ordered items and set them in the OrderDto
        return orders.stream()
                .map(order -> {
                    // Fetch ordered items for the current order
                    List<OrderedItem> orderedItems = orderedItemRepository.findByOrderId(order.getId());

                    // Convert OrderedItem entities to OrderItemDto
                    List<OrderItemDto> orderItemDtos = orderedItems.stream()
                            .map(Mapper::convertOrderItemEntityToDto)
                            .toList();

                    // Convert PurchaseOrder to OrderDto and set the order items
                    OrderDto orderDto = Mapper.convertOrderEntityToDto(order);
                    orderDto.setOrderItemDtos(orderItemDtos);
                    return orderDto;
                })
                .toList();
    }

}
