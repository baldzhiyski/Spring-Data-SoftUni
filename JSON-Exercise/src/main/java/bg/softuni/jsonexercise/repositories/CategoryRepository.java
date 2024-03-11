package bg.softuni.jsonexercise.repositories;

import bg.softuni.jsonexercise.domain.dtos.category.CategoryWithCountAverageAndTotal;
import bg.softuni.jsonexercise.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "select * from product_shop_db.categories order by rand() limit 1", nativeQuery = true)
    Optional<Category> getRandomEntity();


    @Query(value = "SELECT new bg.softuni.jsonexercise.domain.dtos.category.CategoryWithCountAverageAndTotal(c.name, count(p.id), avg(p.price), sum(p.price))" +
            " FROM Product p" +
            " join p.categories" +
            " c group by c.id")
    List<CategoryWithCountAverageAndTotal> findAllOrderByCountOfProducts();


}
