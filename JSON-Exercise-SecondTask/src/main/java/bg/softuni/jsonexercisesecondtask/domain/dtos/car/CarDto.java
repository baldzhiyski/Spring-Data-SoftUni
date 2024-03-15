package bg.softuni.jsonexercisesecondtask.domain.dtos.car;

import bg.softuni.jsonexercisesecondtask.domain.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDto {
    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private BigDecimal travelledDistance;

    public static CarDto fromCar(Car car){
        return  new CarDto(car.getId(),car.getMake(),
                car.getModel(),car.getTravelledDistance());
    }
}
