package bg.softuni.usersystem.repositories;

import bg.softuni.usersystem.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<List<User>> findAllByEmailContaining(String provider);

    @Modifying
    @Transactional
    @Query("update User  u set u.isDeleted = true where u.lastTimeLoggedIn < :date")
    void updateIsDeletedAllUserLastTimeLoggedBefore(LocalDate date);

}
