package bg.softuni.advanced_query_lab.repositories;

import bg.softuni.advanced_query_lab.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient,Long> {

    Optional<List<Ingredient>> findAllByNameStartingWith(String name);
}
