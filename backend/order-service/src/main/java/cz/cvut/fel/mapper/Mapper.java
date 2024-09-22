package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.OrderedItem;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.status.OrderStatus;

/**
 * Utility class responsible for mapping between DTOs and entity objects.
 *
 * This class provides static methods to convert between {@link OrderDto}, {@link PurchaseOrder},
 * {@link OrderItemDto}, and {@link OrderedItem} objects.
 */
public class Mapper {

    /**
     * Converts an {@link OrderDto} to a {@link PurchaseOrder} entity.
     *
     * This method maps the fields of an {@link OrderDto} to a {@link PurchaseOrder} entity,
     * setting the order status to {@link OrderStatus#ORDER_CREATED}.
     *
     * @param dto the {@link OrderDto} to be converted.
     * @return the corresponding {@link PurchaseOrder} entity.
     */
    public static PurchaseOrder convertDtoToOrderEntity(OrderDto dto) {
        return PurchaseOrder.builder()
                .userId(dto.getUserId())
                .orderStatus(OrderStatus.ORDER_CREATED)
                .price(dto.getAmount())
                .build();
    }

    /**
     * Converts a {@link PurchaseOrder} entity to an {@link OrderDto}.
     *
     * This method maps the fields of a {@link PurchaseOrder} entity to an {@link OrderDto}.
     *
     * @param order the {@link PurchaseOrder} entity to be converted.
     * @return the corresponding {@link OrderDto}.
     */
    public static OrderDto convertOrderEntityToDto(PurchaseOrder order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .orderStatus(OrderStatus.ORDER_CREATED)
                .amount(order.getPrice())
                .build();
    }

    /**
     * Converts an {@link OrderItemDto} to an {@link OrderedItem} entity.
     *
     * This method maps the fields of an {@link OrderItemDto} to an {@link OrderedItem} entity.
     *
     * @param orderItemDto the {@link OrderItemDto} to be converted.
     * @return the corresponding {@link OrderedItem} entity.
     */
    public static OrderedItem convertDtoToOrderItemEntity(OrderItemDto orderItemDto) {
        return OrderedItem.builder()
                .productId(orderItemDto.getProductId())
                .orderId(orderItemDto.getOrderId())
                .productName(orderItemDto.getProductName())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }

    /**
     * Converts an {@link OrderedItem} entity to an {@link OrderItemDto}.
     *
     * This method maps the fields of an {@link OrderedItem} entity to an {@link OrderItemDto}.
     *
     * @param orderItem the {@link OrderedItem} entity to be converted.
     * @return the corresponding {@link OrderItemDto}.
     */
    public static OrderItemDto convertOrderItemEntityToDto(OrderedItem orderItem) {
        return OrderItemDto.builder()
                .orderId(orderItem.getOrderId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
