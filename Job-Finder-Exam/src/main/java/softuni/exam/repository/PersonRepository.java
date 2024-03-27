package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Query("SELECT p FROM Person p WHERE p.firstName = :firstName OR p.email = :email OR p.phone = :phone")
    Optional<Person> findFirstByFirstNameOrEmailOrPhone(@Param("firstName") String firstName,
                                                        @Param("email") String email,
                                                        @Param("phone") String phone);

}
