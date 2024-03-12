package bg.softuni.jsonexercisesecondtask.domain.dtos.sale;

import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.part.PartDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {

    private Double discount;
    private String carId;
    private String carMake;
    private String carModel;
    private BigDecimal carTravelledDistance;
    private List<PartDto> carParts;

    public static SaleDto mapFromSale(Sale sale) {
        return Utils.MAPPER.map(sale, SaleDto.class);
    }
}
