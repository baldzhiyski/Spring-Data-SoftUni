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


    @Override
    public String addGamesToShoppingCardOfUserById(String userId) {
        Optional<Set<Game>> allGames = this.gameRepository.findDistinct();

        Long id = Long.valueOf(userId);

        Optional<User> byId = this.userRepository.findById(id);

        if(byId.isEmpty()) return NO_USER_FOUND;

        ShoppingCard shoppingCard = byId.get().getShoppingCard();

        shoppingCard.setGames(allGames.get());

        this.shoppingCardRepository.save(shoppingCard);

        this.userRepository.save(byId.get());

        return String.format(SuccessfulMessages.SUCCESSFULLY_ADDED_GAMES_TO_CARD,byId.get().getFullName());
    }
}
