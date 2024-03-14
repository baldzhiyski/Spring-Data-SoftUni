package bg.softuni.jsonexercise.services.seed;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers(String type) throws FileNotFoundException;

    void seedProducts(String type) throws FileNotFoundException;

    void seedCategories(String type) throws FileNotFoundException;

    default void seedAll(String type) throws FileNotFoundException {
        seedUsers(type);
        seedCategories(type);
        seedProducts(type);
    }
}
