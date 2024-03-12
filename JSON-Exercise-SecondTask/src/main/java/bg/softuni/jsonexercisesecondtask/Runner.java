package bg.softuni.jsonexercisesecondtask;

import bg.softuni.jsonexercisesecondtask.services.customer.CustomerService;
import bg.softuni.jsonexercisesecondtask.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;

    private CustomerService customerService;
    public Runner(SeedService seedService, CustomerService customerService) {
        this.seedService = seedService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAllInputIntoDataBase();
        this.customerService.getAllCustomersOrderByBirthdate();
    }
}
