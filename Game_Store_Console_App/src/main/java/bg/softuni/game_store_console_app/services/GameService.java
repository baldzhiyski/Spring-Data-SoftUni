package bg.softuni.game_store_console_app.services;

public interface GameService {
    String addGame(String[] arguments);

    String editGame(String[] arguments);

    String deleteGame(String[] arguments);

    String displayGames();

    String displayInfoPerGame(String gameName);

    String displayGamesNamesOfLoggedUser();
}
