package bg.softuni.jsonexercisesecondtask.repositories;

import bg.softuni.jsonexercisesecondtask.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartsRepository extends JpaRepository<Part,Long> {
    @Query(value = "select * from" +
            " car_shop_db.parts order by  rand() limit 1",nativeQuery = true)
    Optional<Part> getRandomEntity();
}
