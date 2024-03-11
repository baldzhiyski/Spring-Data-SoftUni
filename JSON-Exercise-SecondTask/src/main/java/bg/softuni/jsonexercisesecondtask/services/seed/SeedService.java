package bg.softuni.jsonexercisesecondtask.services.seed;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedSuppliers() throws FileNotFoundException;

    void seedParts() throws FileNotFoundException;

    void seedCars() throws FileNotFoundException;

    void seedCustomers() throws FileNotFoundException;

    void seedSalesRecords() throws FileNotFoundException;

    default void seedAllInputIntoDataBase() throws FileNotFoundException {
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSalesRecords();
    }
}
