package bg.softuni.jsonexercise.domain.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithCountAverageAndTotal {

    private String name;

    private Long productsCount;

    private Double averagePrice;

    private BigDecimal totalRevenue;
}
