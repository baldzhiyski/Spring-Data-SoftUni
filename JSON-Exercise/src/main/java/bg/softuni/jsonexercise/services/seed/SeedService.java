package bg.softuni.jsonexercise.services.seed;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers(String type) throws FileNotFoundException, JAXBException;

    void seedProducts(String type) throws FileNotFoundException, JAXBException;

    void seedCategories(String type) throws FileNotFoundException, JAXBException;

    default void seedAll(String type) throws FileNotFoundException, JAXBException {
        seedUsers(type);
        seedCategories(type);
        seedProducts(type);
    }
}
