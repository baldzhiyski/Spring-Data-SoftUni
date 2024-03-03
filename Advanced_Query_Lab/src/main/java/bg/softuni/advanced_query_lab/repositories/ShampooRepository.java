package bg.softuni.advanced_query_lab.repositories;

import bg.softuni.advanced_query_lab.entities.Shampoo;
import bg.softuni.advanced_query_lab.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {

    Optional<List<Shampoo>> findAllBySizeOrderById(Size size);

    Optional<List<Shampoo>> findAllBySizeOrLabelIdOrderByPrice(Size size, long labelId);
}
