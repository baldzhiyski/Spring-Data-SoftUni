package bg.softuni.jsonexercisesecondtask.repositories;

import bg.softuni.jsonexercisesecondtask.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository  extends JpaRepository<Car,Long> {
    @Query(value = "select * from" +
            " car_shop_db.cars order by  rand() limit 1",nativeQuery = true)
    Optional<Car> getRandomEntity();

    List<Car> findDistinctByMakeOrderByModelAscTravelledDistanceDesc(String make);



}
