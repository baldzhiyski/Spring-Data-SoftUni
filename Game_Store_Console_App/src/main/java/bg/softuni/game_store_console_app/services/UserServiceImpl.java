package bg.softuni.game_store_console_app.services;

import bg.softuni.game_store_console_app.constants.ErrorMessages;
import bg.softuni.game_store_console_app.entities.Game;
import bg.softuni.game_store_console_app.entities.ShoppingCard;
import bg.softuni.game_store_console_app.entities.User;
import bg.softuni.game_store_console_app.entities.dtos.LogInUserDTO;
import bg.softuni.game_store_console_app.entities.dtos.RegisterUserDTO;
import bg.softuni.game_store_console_app.repositories.GameRepository;
import bg.softuni.game_store_console_app.repositories.ShoppingCardRepository;
import bg.softuni.game_store_console_app.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.*;
import static bg.softuni.game_store_console_app.constants.SuccessfulMessages.*;

@Service
public class UserServiceImpl implements  UserService{
    private UserRepository userRepository;
    private GameRepository gameRepository;

    private ShoppingCardRepository shoppingCardRepository;
    private ModelMapper mapper;


    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, ShoppingCardRepository shoppingCardRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.shoppingCardRepository = shoppingCardRepository;
        this.mapper = mapper;
    }

    @Override
    public String registerUser(String[] arguments) {
        final int argsLength = arguments.length;

        final String email = argsLength > 1 ? arguments[1] : "";
        final String password = argsLength > 2 ? arguments[2] : "";
        final String confirmPassword = argsLength > 3 ? arguments[3] : "";
        final String fullName = argsLength > 4 ? arguments[4] : "";

        RegisterUserDTO userRegisterDto ;

        try{
            userRegisterDto = new RegisterUserDTO(email,password,confirmPassword,fullName);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }

        boolean doesUserExist = this.userRepository.existsByEmail(userRegisterDto.getEmail());

        if(doesUserExist){
            return USER_ALREADY_EXISTS;
        }


        User mappedUser = mapper.map(userRegisterDto, User.class);

        if(this.userRepository.count() == 0){
            mappedUser.setIsAdmin(true);
        }else{
            mappedUser.setIsAdmin(false);
        }
        this.userRepository.save(mappedUser);

        return String.format(SUCCESSFULLY_REGISTER,mappedUser.getFullName());
    }

    @Override
    public String loginUser(String[] arguments) {
        if(this.loggedInUser !=null){
            return USER_ALREADY_LOGGED_IN;
        }

        final int argsLength = arguments.length;

        final String email = argsLength > 1 ? arguments[1] : "";
        final String password = argsLength > 2 ? arguments[2] : "";


        Optional<User> userToLoggedIn = this.userRepository.findByEmail(email);
        if (userToLoggedIn.isEmpty()) {
            return ErrorMessages.NO_USER_FOUND;
        }

        LogInUserDTO userDTO = new LogInUserDTO(email,password) ;

        try {
           userDTO.validate(userToLoggedIn.get().getPassword());
        }catch (IllegalArgumentException e){
            return  e.getMessage();
        }
        this.loggedInUser = userToLoggedIn.get();

        return String.format(SUCCESSFULLY_LOGGED_IN,userToLoggedIn.get().getFullName());
    }

    @Override
    public String logOut() {
        if(this.loggedInUser == null) return NO_USER_LOGGED_IN;

        String fullName = this.loggedInUser.getFullName();
        this.loggedInUser = null;

        return String.format(SUCCESSFULLY_LOGGED_Out,fullName);
    }

    @Override
    public boolean isLoggedUserAdmin() {
        return this.loggedInUser != null && this.loggedInUser.getIsAdmin();
    }

    @Override
    public User getLoggedUser() {
        return this.loggedInUser;
    }

    @Override
    public String addToShoppingCard(String[] arguments) {
        String titleOfGame = arguments[1];

        User loggedUser = getLoggedUser();
        if(loggedUser==null) return NO_USER_LOGGED_IN;

        Optional<Game> byTitle = this.gameRepository.findByTitle(titleOfGame);

        if(byTitle.isEmpty()) return INVALID_GAME_TITLE;
        if (loggedUser.getGames().contains(byTitle.get())) {
            return String.format(ALREADY_OWN_GAME, loggedUser.getFullName(), byTitle.get().getTitle());
        }


        loggedUser.getShoppingCard().getGames().add(byTitle.get());

        this.userRepository.save(loggedUser);

        return String.format(SUCCESSFULLY_ADDED_TO_CARD_GAME,loggedUser.getFullName(),byTitle.get().getTitle());
    }

    @Override
    public String removeGame(String[] arguments) {
        String titleOfGame = arguments[1];
        User loggedUser = getLoggedUser();
        if(loggedUser==null) return NO_USER_LOGGED_IN;

        Optional<Game> byTitle = this.gameRepository.findByTitle(titleOfGame);

        if(byTitle.isEmpty()) return INVALID_GAME_TITLE;

        loggedUser.getShoppingCard().getGames().remove(byTitle.get());

        this.userRepository.save(loggedUser);

        return String.format(SUCCESSFULLY_REMOVED_GAME,loggedUser.getFullName(),byTitle.get().getTitle());

    }

    @Override
    public String buyEverything() {
        User loggedUser = getLoggedUser();
        if(loggedUser==null) return NO_USER_LOGGED_IN;

        if(loggedUser.getShoppingCard().getGames().isEmpty()) return NO_ADDED_GAMES_TO_THE_CARD;

        loggedUser.getShoppingCard().getGames()
                .forEach(game-> loggedUser.getGames().add(game));

        this.userRepository.save(loggedUser);

        String result = loggedUser.getGames()
                .stream().map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));

        loggedUser.getShoppingCard().getGames().clear();

        return String.format(SUCCESSFULLY_BOUGHT_GAMES,loggedUser.getFullName(),result);
    }

    @Override
    public String assignShoppingCard(String[] arguments) {
        Long userId = Long.parseLong(arguments[1]);

        Optional<User> byId = this.userRepository.findById(userId);

        if(byId.isEmpty()) return NO_USER_FOUND;

        ShoppingCard shoppingCard = new ShoppingCard();

        this.shoppingCardRepository.save(shoppingCard);

        byId.get().setShoppingCard(shoppingCard);

        this.userRepository.save(byId.get());

        return String.format(SUCCESSFULLY_ASSIGNED_CARD,byId.get().getFullName());
    }
}
