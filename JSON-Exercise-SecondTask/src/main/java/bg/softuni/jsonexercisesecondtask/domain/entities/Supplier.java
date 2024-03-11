package bg.softuni.jsonexercisesecondtask.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity{

    @OneToMany(targetEntity = Part.class,mappedBy = "supplier")
    private Set<Part> parts;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_Importer")
    private Boolean isImporter;
}
