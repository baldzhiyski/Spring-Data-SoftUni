package bg.softuni.jsonexercise.repositories;

import bg.softuni.jsonexercise.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "select * from product_shop_db.categories order by rand() limit 1", nativeQuery = true)
    Optional<Category> getRandomEntity();


}
