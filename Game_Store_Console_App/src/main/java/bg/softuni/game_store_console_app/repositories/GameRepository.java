package bg.softuni.game_store_console_app.repositories;

import bg.softuni.game_store_console_app.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    Optional<Game> findByTitle(String title);

    Optional<Game> findById(Long id);

    @Query(value = "select distinct g from Game  g ")
    Optional<Set<Game>> findDistinct();
}
