package bg.softuni.jsonexercisesecondtask;

import bg.softuni.jsonexercisesecondtask.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;

    public Runner(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedAllInputIntoDataBase();
    }
}
