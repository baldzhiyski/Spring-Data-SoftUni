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
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDto {

    @XmlElement
    @NotNull
    private String name;


    @XmlElement
    @NotNull
    private String description;

    @XmlElement(name ="start-date")
    private String startDate;

    @XmlElement(name = "is-finished")
    private Boolean isFinished;

    @XmlElement
    @NotNull
    private BigDecimal payment;

    @XmlElement(name = "company")
    @NotNull
    private CompanyDto company;
}
