package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDto {

    @XmlElement(name = "jobTitle")
    @NotNull
    @Size(min = 2,max = 40)
    private String title;

    @XmlElement
    @Min(value = 10)
    private Double hoursAWeek;

    @XmlElement
    @NotNull
    @Min(value = 300)
    private BigDecimal salary;

    @XmlElement
    @Size(min = 5)
    private String description;

    @XmlElement
    private Long companyId;

    public JobDto() {
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(Double hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
