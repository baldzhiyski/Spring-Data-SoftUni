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
}
