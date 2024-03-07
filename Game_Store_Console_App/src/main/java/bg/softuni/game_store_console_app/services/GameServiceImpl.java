package bg.softuni.game_store_console_app.services;

import bg.softuni.game_store_console_app.entities.Game;
import bg.softuni.game_store_console_app.entities.User;
import bg.softuni.game_store_console_app.entities.dtos.AddGameDTO;
import bg.softuni.game_store_console_app.entities.dtos.EditGameDTO;
import bg.softuni.game_store_console_app.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.*;
import static bg.softuni.game_store_console_app.constants.SuccessfulMessages.*;

@Service
public class GameServiceImpl implements GameService{
    private final GameRepository gameRepository;
    private final UserService userService;

    private final ModelMapper mapper;
    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper mapper) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public String addGame(String[] arguments) {
        if(!this.userService.isLoggedUserAdmin()) return NO_PERMISSION;

        int argsLength = arguments.length;

        final String title = argsLength > 1 ? arguments[1] : "";
        final BigDecimal price = argsLength > 2 ? new BigDecimal(arguments[2]) : BigDecimal.ZERO;
        final float size = argsLength > 3 ? Float.parseFloat(arguments[3]) : Float.parseFloat("0.0");
        final String trailer = argsLength > 4 ? arguments[4] : "";
        final String imageURL = argsLength > 5 ? arguments[5] : "";
        final String description = argsLength > 6 ? arguments[6] : "";
        final LocalDate releaseDate = argsLength > 7 ? LocalDate.parse(arguments[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : LocalDate.parse("");


        final AddGameDTO gameDto = new AddGameDTO(title,
                price,
                size,
                trailer,
                imageURL,
                description,
                releaseDate);

        Game mappedGame = this.mapper.map(gameDto, Game.class);

        Optional<Game> byTitle = this.gameRepository.findByTitle(mappedGame.getTitle());

        if(byTitle.isPresent()){
            throw new IllegalArgumentException(ALREADY_EXISTING_GAME);
        }
        this.gameRepository.saveAndFlush(mappedGame);

        return String.format( SUCCESSFULLY_ADDED_GAME,mappedGame.getTitle());
    }

    @Override
    public String editGame(String[] arguments) {
        if(!this.userService.isLoggedUserAdmin()) return NO_PERMISSION;

        Long id = Long.valueOf(arguments[1]);
        final Optional<Game> gameToBeEdited = this.gameRepository.findById(id);

        if(gameToBeEdited.isEmpty()) return INVALID_GAME_ID;

        Map<String, String> updatingArguments = new HashMap<>();

        for (int i = 2; i < arguments.length; i++) {
            String[] argumentsForUpdate = arguments[i].split("=");
            updatingArguments.put(argumentsForUpdate[0], argumentsForUpdate[1]);
        }

        EditGameDTO gameDTO = this.mapper.map(gameToBeEdited, EditGameDTO.class);

        gameDTO.updateFields(updatingArguments);

        Game editedGame = this.mapper.map(gameDTO, Game.class);

        Game gameInBase = gameToBeEdited.get();

        // Update the existing game entity with the edited data
        gameInBase.setDescription(editedGame.getDescription());
        gameInBase.setSize(editedGame.getSize());
        gameInBase.setPrice(editedGame.getPrice());
        gameInBase.setTrailerURL(editedGame.getTrailerURL());
        gameInBase.setImageURL(editedGame.getImageURL());

       // Save the updated game entity back to the database
        this.gameRepository.save(gameInBase);

        return String.format(SUCCESSFULLY_EDITED_GAME,editedGame.getTitle());

    }

    @Override
    public String deleteGame(String[] arguments) {
        if(!this.userService.isLoggedUserAdmin()) return NO_PERMISSION;

        Optional<Game> gameById = this.gameRepository.findById(Long.valueOf(arguments[1]));

        if(gameById.isEmpty()){
            throw new IllegalArgumentException(INVALID_GAME_ID);
        }

        String title = gameById.get().getTitle();

        this.gameRepository.delete(gameById.get());

        return String.format(SUCCESSFULLY_DELETED_GAME,title);
    }

    @Override
    public String displayGames() {
       return this.gameRepository.findAll()
                .stream()
                .map(Game::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String displayInfoPerGame(String gameName) {
        Optional<Game> byTitle = this.gameRepository.findByTitle(gameName);

        if(byTitle.isEmpty()) return INVALID_GAME_TITLE;

        Game game = byTitle.get();

       return game.detailedDescription();
    }

    @Override
    public String displayGamesNamesOfLoggedUser() {
        User loggedUser = this.userService.getLoggedUser();

        if(loggedUser==null) return No;

        return loggedUser.getGames()
                .stream().map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
