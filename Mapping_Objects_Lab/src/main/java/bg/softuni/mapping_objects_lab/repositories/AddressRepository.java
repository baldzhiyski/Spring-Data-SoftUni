package bg.softuni.mapping_objects_lab.repositories;

import bg.softuni.mapping_objects_lab.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByCountryAndCity(String country, String city);
}
