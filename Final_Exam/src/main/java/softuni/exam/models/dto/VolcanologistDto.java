package softuni.exam.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistDto {

    @XmlElement(name = "first_name")
    @Size(min = 2,max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2,max = 30)
    @NotNull
    private String lastName;

    @XmlElement
    @NotNull
    private Double salary;

    @XmlElement
    @Min(value = 18)
    @Max(value = 80)
    @NotNull
    private Integer age;

    @XmlElement(name = "exploring_from")
    private String exploringFrom;

    @XmlElement(name = "exploring_volcano_id")
    private Long volcano;

    public VolcanologistDto() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Long getVolcano() {
        return volcano;
    }

    public void setVolcano(Long volcano) {
        this.volcano = volcano;
    }
}
