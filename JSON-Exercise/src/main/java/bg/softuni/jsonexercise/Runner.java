package bg.softuni.jsonexercise;

import bg.softuni.jsonexercise.services.category.CategoryService;
import bg.softuni.jsonexercise.services.product.ProductService;
import bg.softuni.jsonexercise.services.seed.SeedService;
import bg.softuni.jsonexercise.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;
    private ProductService productService;
    private UserService userService;

    private CategoryService categoryService;

    public Runner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.productService.getAllInSpecificRange(
                BigDecimal.valueOf(500)
                ,BigDecimal.valueOf(1000));
    }
}
