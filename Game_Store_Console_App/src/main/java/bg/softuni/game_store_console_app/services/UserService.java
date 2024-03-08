package bg.softuni.game_store_console_app.services;

import bg.softuni.game_store_console_app.entities.User;

public interface UserService {
    String registerUser(String[] arguments);

    String loginUser(String[] arguments);

    String logOut();

    boolean isLoggedUserAdmin();

    User getLoggedUser();

    String addToShoppingCard(String[] arguments);

    String removeGame(String[] arguments);

    String buyEverything();

}
