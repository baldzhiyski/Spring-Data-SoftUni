package bg.softuni.game_store_console_app;

import bg.softuni.game_store_console_app.services.GameService;
import bg.softuni.game_store_console_app.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static bg.softuni.game_store_console_app.constants.Commands.*;

@Component
public class RunnerApp implements CommandLineRunner {
    private Scanner scanner;
    private UserService userService;

    private GameService gameService;

    public RunnerApp(Scanner scanner, UserService userService, GameService gameService) {
        this.scanner = scanner;
        this.userService = userService;
        this.gameService = gameService;
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
                case ADD_GAME -> this.gameService.addGame(arguments);
                case EDIT_GAME -> this.gameService.editGame(arguments);
                case DELETE_GAME -> this.gameService.deleteGame(arguments);
                case ALL_GAMES -> this.gameService.displayGames();
                case DETAILS_PER_GAME -> this.gameService.displayInfoPerGame(arguments[1]);
                case OWNED_GAMES -> this.gameService.displayGamesNamesOfLoggedUser();
                case PURCHASE_GAME -> this.userService.purchaseGame(arguments);
                default -> "No such command found";
            };
            System.out.println(output);
        }
    }
}
