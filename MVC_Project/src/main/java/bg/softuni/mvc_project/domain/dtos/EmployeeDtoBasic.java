package bg.softuni.mvc_project.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDtoBasic {

    @XmlElement(name = "first-name")
    @NotNull
    private String firstName;

    @XmlElement(name = "last-name")
    @NotNull
    private String lastName;

    @XmlElement
    @NotNull
    private Integer age;

    @XmlElement(name = "project")
    @NotNull
    private String projectName;

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", project=" + projectName +
                '}';
    }
}