package softuni.exam.models.entity;



import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "constellations")
public class Constellation extends BaseEntity{

    @Column(nullable = false)
    @Size(min = 5)
    private String description;

    @Column(unique = true,nullable = false)
    private String name;

    @OneToMany(mappedBy = "constellation")
    private List<Star> stars;

    public Constellation() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> star) {
        this.stars = star;
    }
}
