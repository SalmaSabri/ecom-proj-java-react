package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Data Transfer Object representing a payment.
 *
 * Contains payment details such as order ID, user ID, and the total amount paid.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    private Long orderId;
    private String userId;
    private BigDecimal amount;

}
