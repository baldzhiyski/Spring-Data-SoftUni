package bg.softuni.jsonexercise;

import bg.softuni.jsonexercise.services.product.ProductService;
import bg.softuni.jsonexercise.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;
    private ProductService productService;

    public Runner(SeedService seedService, ProductService productService) {
        this.seedService = seedService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.productService.getAllInSpecificRange(new BigDecimal(500), new BigDecimal(1000));
    }
}
