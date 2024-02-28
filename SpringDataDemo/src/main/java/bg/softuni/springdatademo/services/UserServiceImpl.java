package bg.softuni.springdatademo.services;

import bg.softuni.springdatademo.exceptions.EntityMissingException;
import bg.softuni.springdatademo.models.Account;
import bg.softuni.springdatademo.models.User;
import bg.softuni.springdatademo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, int age) {
        if(username.isBlank() || age<18){
            throw new EntityMissingException("Validation Failed !");
        }

        Optional<User> byUsername = this.userRepository.findByUsername(username);

        if(byUsername.isPresent()){
            throw new EntityMissingException("Username already exists!");
        }

        Account account = new Account();
        User user = new User(username,age,account);

        this.userRepository.save(user);
    }
}
