package cz.cvut.fel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ordered_item_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedItem {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer orderId;
    private String productId;
    private String productName;
    private BigDecimal quantity;
    private BigDecimal price;
}
