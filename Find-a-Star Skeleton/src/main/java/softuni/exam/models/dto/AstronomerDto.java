package softuni.exam.models.dto;

import org.apache.tomcat.jni.Local;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Date;


@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerDto {
    @XmlElement(name = "average_observation_hours")
    @Min(value = 500)
    private Double averageObservationHours;

    @XmlElement(name = "birthday")
    private String birthDay;

    @XmlElement(name = "first_name")
    @Size(min = 2,max = 30)
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2,max = 30)
    private String lastName;

    @XmlElement(name = "observing_star_id")
    private Long observingStarId;
    @XmlElement
    @Min(value = 15000)
    private Double salary;


    public AstronomerDto(Double averageObservationHours, String birthDay, String firstName, String lastName, Double salary, Long observingStarId) {
        this.averageObservationHours = averageObservationHours;
        this.birthDay = birthDay;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.observingStarId = observingStarId;
    }

    public AstronomerDto() {
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(Long observingStarId) {
        this.observingStarId = observingStarId;
    }
}
