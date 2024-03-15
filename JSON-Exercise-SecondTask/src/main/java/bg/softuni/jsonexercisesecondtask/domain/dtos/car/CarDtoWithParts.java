package bg.softuni.jsonexercisesecondtask.domain.dtos.car;

import bg.softuni.jsonexercisesecondtask.domain.dtos.part.PartDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDtoWithParts {
    private Long id;
    private String make;

    private String model;
    private BigDecimal travelledDistance;

    @XmlElementWrapper(name = "parts")
    @XmlElement(name = "part")
    private List<PartDto> parts;

    public static CarDtoWithParts fromCar(Car car){
        List<PartDto> partDtos = car.getParts().stream()
                .map(PartDto::fromPart)
                .collect(Collectors.toList());

        return new CarDtoWithParts(car.getId(),car.getMake(),car.getModel(),
                car.getTravelledDistance(),partDtos);
    }
}
