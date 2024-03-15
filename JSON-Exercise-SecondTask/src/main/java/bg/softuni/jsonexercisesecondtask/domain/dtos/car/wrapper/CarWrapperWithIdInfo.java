package bg.softuni.jsonexercisesecondtask.domain.dtos.car.wrapper;

import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWrapperWithIdInfo {

    @XmlElement(name = "car")
    private List<CarDto> cars;
}
