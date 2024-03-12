package bg.softuni.jsonexercisesecondtask.domain.dtos.car;

import bg.softuni.jsonexercisesecondtask.domain.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String make;

    private String model;
    private BigDecimal travelledDistance;

    public static CarDto fromCar(Car car){
        return  new CarDto(car.getId(),car.getMake(),
                car.getModel(),car.getTravelledDistance());
    }
}
