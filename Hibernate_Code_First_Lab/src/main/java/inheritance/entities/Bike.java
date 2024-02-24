package inheritance.entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Bike extends Vehicle{
    private static final String BIKE_TYPE = "BIKE";
    public Bike(String model, BigDecimal price, String fuelType) {
        super(BIKE_TYPE, model, price, fuelType);
    }

    public Bike() {

    }
}
