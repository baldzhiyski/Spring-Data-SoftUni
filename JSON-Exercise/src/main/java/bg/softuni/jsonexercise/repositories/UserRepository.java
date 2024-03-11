package bg.softuni.jsonexercise.repositories;

import bg.softuni.jsonexercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from product_shop_db.users order by rand() limit 1", nativeQuery = true)
    Optional<User> getRandomEntity();
}
