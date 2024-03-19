package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.DayOfWeek;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forcasts")
public class ForeCast extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    @DecimalMin(value = "-20", message = "Value must be greater than or equal to -20")
    @DecimalMax(value = "60", message = "Value must be less than or equal to 60")
    private Double maxTemperature;

    @Column(nullable = false)
    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    private Double minTemperature;

    @Column()
    private LocalTime sunrise;

    @Column()
    private LocalTime sunset;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
