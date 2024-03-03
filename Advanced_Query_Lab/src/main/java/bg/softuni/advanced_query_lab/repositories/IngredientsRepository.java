package bg.softuni.advanced_query_lab.repositories;

import bg.softuni.advanced_query_lab.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient,Long> {

    Optional<List<Ingredient>> findAllByNameStartingWith(String name);

    Optional<List<Ingredient>> findAllByNameIn(List<String> names);


    void deleteAllByName(String name);

    @Modifying
    @Query(value = "update Ingredient  i " +
            "set i.price = i.price * :price")
    void updateAllPrice(BigDecimal price);

    @Modifying
    @Query(value = "update Ingredient  i " +
            "set i.price = i.price * 1.1" +
            " where i.name in :names")
    void updateAllPriceWhereNameIn(List<String> names);
}
