package cz.cvut.fel.dto;

import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer orderId;
    private String userId;
    private BigDecimal amount;
    private OrderStatus orderStatus;
}
