package bg.softuni.usersystem.services;

import bg.softuni.usersystem.domain.entities.User;
import bg.softuni.usersystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsersByEmailProvider(String provider){
        return this.userRepository.findAllByEmailContaining(provider)
                .get();
    }

    @Override
    public void setFieldIsDeletedTrueAllUsersNotLoggedAfterDate(LocalDate date) {
        this.userRepository.updateIsDeletedAllUserLastTimeLoggedBefore(date);
    }
}
