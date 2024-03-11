package bg.softuni.jsonexercise;

import bg.softuni.jsonexercise.services.product.ProductService;
import bg.softuni.jsonexercise.services.seed.SeedService;
import bg.softuni.jsonexercise.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;
    private ProductService productService;
    private UserService userService;

    public Runner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.getAllUsersWith1SoldItemAndActiveBuyer();
    }
}
