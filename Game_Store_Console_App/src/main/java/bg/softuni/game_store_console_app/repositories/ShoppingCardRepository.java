package bg.softuni.game_store_console_app.repositories;

import bg.softuni.game_store_console_app.entities.ShoppingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCardRepository extends JpaRepository<ShoppingCard,Long> {

}
