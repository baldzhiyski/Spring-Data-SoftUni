package bg.softuni.game_store_console_app.services;

import bg.softuni.game_store_console_app.constants.SuccessfulMessages;
import bg.softuni.game_store_console_app.entities.Game;
import bg.softuni.game_store_console_app.entities.ShoppingCard;
import bg.softuni.game_store_console_app.entities.User;
import bg.softuni.game_store_console_app.repositories.GameRepository;
import bg.softuni.game_store_console_app.repositories.ShoppingCardRepository;
import bg.softuni.game_store_console_app.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.NO_USER_FOUND;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService{
    private ShoppingCardRepository shoppingCardRepository;

    private GameRepository gameRepository;

    private UserRepository userRepository;

    public ShoppingCardServiceImpl(ShoppingCardRepository shoppingCardRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.shoppingCardRepository = shoppingCardRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }


}
