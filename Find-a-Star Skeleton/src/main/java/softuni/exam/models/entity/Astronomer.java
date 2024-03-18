package softuni.exam.models.entity;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "astronomers")
public class Astronomer extends BaseEntity{
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Min(value = 15000)
    private Double salary;

    @Column(nullable = false,name = "average_observation_hours")
    private Double averageObservationHours;

    @Column(name = "birthday")
    private LocalDate birthDay;

    @ManyToOne
    @JoinColumn(name = "observing_star_id")
    private Star star;

    public Astronomer(String firstName, String lastName, Double salary, Double averageObservationHours, LocalDate birthDay, Star star) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.averageObservationHours = averageObservationHours;
        this.birthDay = birthDay;
        this.star = star;
    }

    public Astronomer() {
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

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }
}
