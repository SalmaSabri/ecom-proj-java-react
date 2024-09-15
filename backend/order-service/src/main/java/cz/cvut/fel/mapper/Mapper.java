package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.status.OrderStatus;

public class Mapper {

    public static PurchaseOrder convertDtoToEntity(OrderDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(dto.getAmount());
        return purchaseOrder;
    }

    public static OrderDto convertEntityToDto(PurchaseOrder order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setProductId(order.getProductId());
        dto.setUserId(order.getUserId());
        dto.setOrderStatus(OrderStatus.ORDER_CREATED);
        dto.setAmount(order.getPrice());
        return dto;
    }
}
