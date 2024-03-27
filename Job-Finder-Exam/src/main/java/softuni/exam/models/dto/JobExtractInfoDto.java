package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

public class JobExtractInfoDto {
    private String title;

    private Double hoursAWeek;

    private BigDecimal salary;

    public JobExtractInfoDto(String title, Double hoursAWeek, BigDecimal salary) {
        this.title = title;
        this.hoursAWeek = hoursAWeek;
        this.salary = salary;
    }

    public JobExtractInfoDto() {
    }

    @Override
    public String toString() {
        return String.format("Job title %s\n" +
                "-Salary: %.2f $\n" +
                "--Hours a week: %.2f h.",title,salary,hoursAWeek);
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
