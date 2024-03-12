package bg.softuni.jsonexercisesecondtask;

import bg.softuni.jsonexercisesecondtask.services.car.CarService;
import bg.softuni.jsonexercisesecondtask.services.customer.CustomerService;
import bg.softuni.jsonexercisesecondtask.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;

    private CustomerService customerService;
    private CarService carService;
    @Autowired
    public Runner(SeedService seedService, CustomerService customerService, CarService carService) {
        this.seedService = seedService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAllInputIntoDataBase();
//        this.customerService.getAllCustomersOrderByBirthdate();
        this.carService.getAllCarsMadeFromToyota("Toyota");
    }
}
