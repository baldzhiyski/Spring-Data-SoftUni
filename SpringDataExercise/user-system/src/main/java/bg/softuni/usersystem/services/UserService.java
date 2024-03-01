package bg.softuni.usersystem.services;


import bg.softuni.usersystem.domain.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getUsersByEmailProvider(String provider);

    void setFieldIsDeletedTrueAllUsersNotLoggedAfterDate(LocalDate date);
}
