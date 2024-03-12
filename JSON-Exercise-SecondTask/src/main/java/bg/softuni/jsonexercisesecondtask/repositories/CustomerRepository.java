package bg.softuni.jsonexercisesecondtask.repositories;

import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerWthCarsAndMoneyDto;
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

    @Query("SELECT new bg.softuni.jsonexercisesecondtask.domain.dtos.customer." +
            "CustomerWthCarsAndMoneyDto(c.name, COUNT(c.id),ROUND(SUM((1 + s.discount) * p.price * p.quantity),2)) " +
            "FROM Customer c " +
            "LEFT JOIN c.sales s " +
            "LEFT JOIN s.car.parts p " +
            "GROUP BY c.id " +
            "HAVING COUNT(c.id) > 1")
    List<CustomerWthCarsAndMoneyDto> findAllByCountOfCars();

}
