package inheritance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_vehicle")
public abstract class Vehicle extends IdType{
    @Basic
    private String module;
    @Basic
    private BigDecimal price;

    @Column(name = "fuel_type")
    private String fuelType;

    protected Vehicle(){}
    protected Vehicle(String type,String model,BigDecimal price, String fuelType){
        this.type = type;
        this.module = model;
        this.price=price;
        this.fuelType = fuelType;
    }
    protected Vehicle(String type){
        this.type = type;
    }


}
