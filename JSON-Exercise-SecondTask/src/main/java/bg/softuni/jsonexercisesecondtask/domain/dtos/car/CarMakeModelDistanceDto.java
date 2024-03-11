package bg.softuni.jsonexercisesecondtask.domain.dtos.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarMakeModelDistanceDto {
    private String make;

    private String model;

    private BigDecimal travelDistance;
}
