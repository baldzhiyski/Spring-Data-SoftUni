package bg.softuni.advanced_query_lab;

import bg.softuni.advanced_query_lab.services.IngredientService;
import bg.softuni.advanced_query_lab.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private ShampooService shampooService;
    private IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        this.shampooService.getAllShampoosNamesWhereIngredientsCountIs(count)
                .forEach(System.out::println);
    }
}
