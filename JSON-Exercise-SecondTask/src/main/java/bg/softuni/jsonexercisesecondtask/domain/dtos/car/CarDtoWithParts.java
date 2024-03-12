package bg.softuni.jsonexercisesecondtask.domain.dtos.car;

import bg.softuni.jsonexercisesecondtask.domain.dtos.part.PartDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoWithParts {
    private Long id;
    private String make;

    private String model;
    private BigDecimal travelledDistance;

    private List<PartDto> parts;

    public static CarDtoWithParts fromCar(Car car){
        List<PartDto> partDtos = car.getParts().stream()
                .map(PartDto::fromPart)
                .collect(Collectors.toList());

        return new CarDtoWithParts(car.getId(),car.getMake(),car.getModel(),
                car.getTravelledDistance(),partDtos);
    }
}
