package bg.softuni.jsonexercisesecondtask.domain.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWthCarsAndMoneyDto {

    private String fullName;

    private Long boughtCars;

    private double spentMoney;


}
