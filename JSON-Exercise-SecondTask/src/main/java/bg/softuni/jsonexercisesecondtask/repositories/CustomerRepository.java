package bg.softuni.jsonexercisesecondtask.repositories;

import bg.softuni.jsonexercisesecondtask.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select * from car_shop_db.customers  order by rand() limit 1",nativeQuery = true)
    Optional<Customer> getRandomEntity();


    @Query(value = "select c from Customer  c order by c.birthdate,c.isYoungDriver DESC ")
    List<Customer> findAllOrderByBirthdate();
}
