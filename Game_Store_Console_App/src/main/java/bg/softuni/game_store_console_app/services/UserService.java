package bg.softuni.game_store_console_app.services;

public interface UserService {
    String registerUser(String[] arguments);

    String loginUser(String[] arguments);

    String logOut();

    boolean isLoggedUserAdmin();
}
