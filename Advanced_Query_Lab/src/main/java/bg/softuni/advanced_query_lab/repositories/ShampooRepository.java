package bg.softuni.advanced_query_lab.repositories;

import bg.softuni.advanced_query_lab.entities.Shampoo;
import bg.softuni.advanced_query_lab.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {

    Optional<List<Shampoo>> findAllBySizeOrderById(Size size);

    Optional<List<Shampoo>> findAllBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    Optional<List<Shampoo>> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    @Query(value = "select distinct s.brand from Shampoo as s join s.ingredients as  i where  i.name in :names")
    Optional<List<String>> findAllByIngredientsNameIn(List<String> names);
}
