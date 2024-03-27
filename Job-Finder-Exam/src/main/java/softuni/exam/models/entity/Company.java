package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{
    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private String website;

    @Column(name = "date_established",nullable = false)
    private LocalDate dateEstablished;

    @OneToMany
    @JoinTable
    private List<Job> jobs;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
