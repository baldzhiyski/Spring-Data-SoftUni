package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    boolean isDataSeeded();

    void seedCategories(List<Category> categories);

    Set<Category> getRandomCategories();

}
