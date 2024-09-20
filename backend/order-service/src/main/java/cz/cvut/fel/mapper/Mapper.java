package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.OrderedItem;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.status.OrderStatus;

public class Mapper {

    public static PurchaseOrder convertDtoToEntity(OrderDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(dto.getAmount());
        return purchaseOrder;
    }

    public static OrderDto convertEntityToDto(PurchaseOrder order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setOrderStatus(OrderStatus.ORDER_CREATED);
        dto.setAmount(order.getPrice());
        return dto;
    }

    // Convert OrderItemDto to OrderedItem
    public static OrderedItem toEntity(OrderItemDto orderItemDto) {
        return OrderedItem.builder()
                .productId(orderItemDto.getProductId())
                .orderId(orderItemDto.getOrderId())
                .productName(orderItemDto.getProductName())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }
}
