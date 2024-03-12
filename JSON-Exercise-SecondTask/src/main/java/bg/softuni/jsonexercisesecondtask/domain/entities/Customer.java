package bg.softuni.jsonexercisesecondtask.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer  extends  BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date")
    private Date birthdate;

    @Column(name = "is_young_driver")
    private Boolean isYoungDriver;

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Sale> sales;
}
