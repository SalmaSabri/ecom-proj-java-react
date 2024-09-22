package cz.cvut.fel.dto;

import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


/**
 * Data Transfer Object representing an order.
 *
 * Contains order details, such as order ID, user ID, total amount, order status, and a list of order items.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;
    private String userId;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtos;
}
