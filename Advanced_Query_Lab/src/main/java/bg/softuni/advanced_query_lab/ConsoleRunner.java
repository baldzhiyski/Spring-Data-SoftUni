package bg.softuni.advanced_query_lab;

import bg.softuni.advanced_query_lab.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private ShampooService shampooService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String price = scanner.nextLine();

        this.shampooService.getAllShampoosWithGreaterPriceThan(new BigDecimal(price))
                .forEach(System.out::println);

    }
}
