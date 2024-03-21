package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ofers")
public class Offer  extends BaseEntity{

    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "published_on",nullable = false)
    private LocalDate publishedOn;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH
    })
    private Apartment apartment;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH
    })
    private Agent agent;
}
