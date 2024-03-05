package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    boolean isDataSeeded();

    void seedCategories(List<Category> categories);

    Set<Category> getRandomCategories();

}
