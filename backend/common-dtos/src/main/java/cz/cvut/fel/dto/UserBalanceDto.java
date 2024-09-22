package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing the balance of a user.
 *
 * Contains user ID and the balance amount.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalanceDto {
    private String userId;
    private BigDecimal price;
}
