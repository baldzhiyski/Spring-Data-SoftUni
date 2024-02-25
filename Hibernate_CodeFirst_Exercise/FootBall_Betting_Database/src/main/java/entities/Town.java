package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "towns")
public class Town extends IdName{

    public Town() {
    }

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
