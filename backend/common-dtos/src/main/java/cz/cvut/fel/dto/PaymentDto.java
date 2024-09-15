package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Integer orderId;
    private String userId;
    private BigDecimal amount;

}
