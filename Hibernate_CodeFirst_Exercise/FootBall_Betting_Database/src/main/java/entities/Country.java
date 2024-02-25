package entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(length = 3, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "countries_continents",
    joinColumns = @JoinColumn(name = "country_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "continent_id",referencedColumnName = "id"))
    private Set<Continent> continentsList;

    public Country() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Continent> getContinentsList() {
        return continentsList;
    }

    public void setContinentsList(Set<Continent> continentsList) {
        this.continentsList = continentsList;
    }
}
