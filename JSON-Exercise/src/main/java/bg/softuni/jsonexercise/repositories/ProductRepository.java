package bg.softuni.jsonexercise.repositories;

import bg.softuni.jsonexercise.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from product_shop_db.products order by rand() limit 1", nativeQuery = true)
    Optional<Product> getRandomEntity();
}
