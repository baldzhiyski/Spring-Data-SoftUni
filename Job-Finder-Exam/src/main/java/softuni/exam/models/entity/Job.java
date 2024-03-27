package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(name = "hours_a_week")
    private Double hoursAWeek;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Job() {
    }

    public Job(String title, BigDecimal salary, Double hoursAWeek, String description, Company company) {
        this.title = title;
        this.salary = salary;
        this.hoursAWeek = hoursAWeek;
        this.description = description;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
