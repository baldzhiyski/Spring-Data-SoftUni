package inheritance;

import inheritance.entities.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        Vehicle car = new Car("Corsa", BigDecimal.TEN,"Petrol",5);
        Vehicle bike = new Bike("BMX",BigDecimal.TWO,"None");
        Vehicle plane = new Plane("Boeing",BigDecimal.ONE,"PlaneFuel",100);
        Vehicle truck = new Truck("Scania",BigDecimal.ONE,"Diesel",40);

        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
