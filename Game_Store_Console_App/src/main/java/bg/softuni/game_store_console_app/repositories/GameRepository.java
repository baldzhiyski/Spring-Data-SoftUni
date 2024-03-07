package bg.softuni.game_store_console_app.repositories;

import bg.softuni.game_store_console_app.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    Optional<Game> findByTitle(String title);

    Optional<Game> findById(Long id);
}
