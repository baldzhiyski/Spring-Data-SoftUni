package bg.softuni.jsonexercisesecondtask.services.seed;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SeedService {
    void seedSuppliers(String inputType) throws FileNotFoundException, JAXBException;

    void seedParts(String inputType) throws FileNotFoundException, JAXBException;

    void seedCars(String inputType) throws FileNotFoundException, JAXBException;

    void seedCustomers(String inputType) throws FileNotFoundException, JAXBException;

    void seedSalesRecords() throws FileNotFoundException;

    default void seedAllInputIntoDataBase(String inputType) throws FileNotFoundException, JAXBException {
        seedSuppliers(inputType);
        seedParts(inputType);
        seedCars(inputType);
        seedCustomers(inputType);
        seedSalesRecords();
    }
}
