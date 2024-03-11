package bg.softuni.jsonexercise.domain.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithBuyerNames {
    private String name;

    private BigDecimal price;

    private String buyerFirstName;

    private String buyerLastName;
}
