package bg.softuni.jsonexercisesecondtask.domain.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerNameBirthDateDriverDto {
    @XmlAttribute
    private String name;
    @XmlElement(name = "birth-date")
    private Date birthDate;
    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;
}
