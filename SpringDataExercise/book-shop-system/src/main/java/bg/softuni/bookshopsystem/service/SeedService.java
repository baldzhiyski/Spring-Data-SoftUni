package bg.softuni.bookshopsystem.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SeedService {
    void seedAuthors() throws IOException;

    void seedBooks() throws IOException;

    void seedCategories() throws IOException;

    // default method doesn't need to be overwritten and it would start all seeding methods
    default void seedAllData() throws IOException {
        seedCategories();
        seedAuthors();
        seedBooks();
    }
}
