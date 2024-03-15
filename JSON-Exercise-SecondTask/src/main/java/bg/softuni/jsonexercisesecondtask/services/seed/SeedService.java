package bg.softuni.jsonexercisesecondtask.services.seed;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedSuppliers(String inputType) throws FileNotFoundException;

    void seedParts(String inputType) throws FileNotFoundException;

    void seedCars(String inputType) throws FileNotFoundException;

    void seedCustomers(String inputType) throws FileNotFoundException;

    void seedSalesRecords() throws FileNotFoundException;

    default void seedAllInputIntoDataBase(String inputType) throws FileNotFoundException {
        seedSuppliers(inputType);
        seedParts(inputType);
        seedCars(inputType);
        seedCustomers(inputType);
        seedSalesRecords();
    }
}
