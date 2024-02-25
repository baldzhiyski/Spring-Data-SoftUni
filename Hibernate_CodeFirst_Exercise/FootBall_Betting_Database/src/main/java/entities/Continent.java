package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "continents")
public class Continent extends IdName{

    public Continent(){}

}
