package inheritance;

import inheritance.entities.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();


        Company company = new Company("RyanAir");

        PlateNumber plateNumber = new PlateNumber("PB02310LP");
        Car car = new Car("Corsa", BigDecimal.TEN,"Petrol",5,plateNumber);
        Bike bike = new Bike("BMX",BigDecimal.TWO,"None");
        Plane plane = new Plane("Boeing",BigDecimal.ONE,"PlaneFuel",100,company);
        Vehicle truck = new Truck("Scania",BigDecimal.ONE,"Diesel",40);


        persistEntities(entityManager, company, plateNumber, car, bike, plane, truck);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void persistEntities(EntityManager entityManager, Company company, PlateNumber plateNumber, Vehicle car, Vehicle bike, Plane plane, Vehicle truck) {
        entityManager.persist(company);
        entityManager.persist(plateNumber);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);
    }
}
