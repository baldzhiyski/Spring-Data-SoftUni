package bg.softuni.game_store_console_app;

import bg.softuni.game_store_console_app.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static bg.softuni.game_store_console_app.constants.Commands.*;

@Component
public class RunnerApp implements CommandLineRunner {
    private Scanner scanner;
    private UserService userService;

    public RunnerApp(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("close")){
            final String[] arguments = input.split("\\|");
            final String command = arguments[0];

           final String output= switch (command){
                case REGISTER_USER -> this.userService.registerUser(arguments);
                case LOG_IN_USER -> this.userService.loginUser(arguments);
               case LOG_OUT -> this.userService.logOut();
                default -> "No such command found";
            };
            System.out.println(output);
        }
    }
}
