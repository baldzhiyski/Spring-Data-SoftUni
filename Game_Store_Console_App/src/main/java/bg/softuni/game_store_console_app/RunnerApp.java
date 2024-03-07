package bg.softuni.game_store_console_app;

import bg.softuni.game_store_console_app.services.GameService;
import bg.softuni.game_store_console_app.services.ShoppingCardService;
import bg.softuni.game_store_console_app.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static bg.softuni.game_store_console_app.constants.Commands.*;

@Component
public class RunnerApp implements CommandLineRunner {
    private Scanner scanner;
    private UserService userService;
    private ShoppingCardService shoppingCardService;

    private GameService gameService;

    public RunnerApp(Scanner scanner, UserService userService, ShoppingCardService shoppingCardService, GameService gameService) {
        this.scanner = scanner;
        this.userService = userService;
        this.shoppingCardService = shoppingCardService;
        this.gameService = gameService;
    }

    // In order to work we should assign first each user with a card and then we should add some games to the card of
    // user and then we can say buyItem or removeItem
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
                case ADD_GAME_TO_CARD -> this.userService.addToShoppingCard(arguments);
                case REMOVE_GAME -> this.userService.removeGame(arguments);
                case ASSIGN_SHOPPING_CARD -> this.userService.assignShoppingCard(arguments);
                case PURCHASE_EVERYTHING -> this.userService.buyEverything();
                case ADD_GAMES_TO_CARD -> this.shoppingCardService.addGamesToShoppingCardOfUserById(arguments[1]);
                default -> "No such command found";
            };
            System.out.println(output);
        }
    }
}
