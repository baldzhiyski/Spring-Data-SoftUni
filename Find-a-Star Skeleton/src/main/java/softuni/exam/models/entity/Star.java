package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.StarType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "stars")
public class Star extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String name;

    @Column(name = "light_years",nullable = false)
    @Positive
    private Double lightYears;

    @Column(columnDefinition = "TEXT",nullable = false)
    @Size(min = 5)
    private String description;

    @Column(name = "star_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private StarType starType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "constellation_id",referencedColumnName = "id")
    private Constellation constellation;

    @OneToMany(mappedBy = "star")
    private List<Astronomer> astronomers;

    public Star() {
    }

    public Star(String name, Double lightYears, String description, StarType starType, Constellation constellation, List<Astronomer> astronomers) {
        this.name = name;
        this.lightYears = lightYears;
        this.description = description;
        this.starType = starType;
        this.constellation = constellation;
        this.astronomers = astronomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StarType getStarType() {
        return starType;
    }

    public void setStarType(StarType starType) {
        this.starType = starType;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    public List<Astronomer> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<Astronomer> astronomers) {
        this.astronomers = astronomers;
    }
}
