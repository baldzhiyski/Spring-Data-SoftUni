package bg.softuni.jsonexercise.services.category;

import bg.softuni.jsonexercise.constants.Paths;
import bg.softuni.jsonexercise.constants.Utils;
import bg.softuni.jsonexercise.domain.dtos.category.CategoryWithCountAverageAndTotal;
import bg.softuni.jsonexercise.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryWithCountAverageAndTotal> getCategoriesStatistics() throws IOException {
        List<CategoryWithCountAverageAndTotal> allOrderByCountOfProducts = this.categoryRepository.findAllOrderByCountOfProducts();


        Utils.writeJsonOnFile(allOrderByCountOfProducts, Path.of(Paths.PATH_TO_CATEGORIES_BY_PRODUCT_COUNT));

        return allOrderByCountOfProducts;

    }
}
