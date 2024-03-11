package bg.softuni.jsonexercisesecondtask.domain.dtos.part;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartDto {
    private String name;

    private BigDecimal price;

    private Integer quantity;
}
