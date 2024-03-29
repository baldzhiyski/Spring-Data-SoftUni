package bg.softuni.jsonexercisesecondtask.repositories;

import bg.softuni.jsonexercisesecondtask.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query(value = "select * from car_shop_db.suppliers order by rand() limit 1",nativeQuery = true)
    Optional<Supplier> getARandomEntity();

    List<Supplier> findAllByIsImporterIsFalse();
}
