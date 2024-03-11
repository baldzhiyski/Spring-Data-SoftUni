package bg.softuni.jsonexercise.services.category;

import bg.softuni.jsonexercise.domain.dtos.category.CategoryWithCountAverageAndTotal;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<CategoryWithCountAverageAndTotal> getCategoriesStatistics() throws IOException;
}
