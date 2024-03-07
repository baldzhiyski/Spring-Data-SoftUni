package bg.softuni.game_store_console_app.services;

import bg.softuni.game_store_console_app.constants.ErrorMessages;
import bg.softuni.game_store_console_app.entities.User;
import bg.softuni.game_store_console_app.entities.dtos.LogInUserDTO;
import bg.softuni.game_store_console_app.entities.dtos.RegisterUserDTO;
import bg.softuni.game_store_console_app.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.*;
import static bg.softuni.game_store_console_app.constants.SuccessfulMessages.*;

@Service
public class UserServiceImpl implements  UserService{
    private UserRepository userRepository;
    private ModelMapper mapper;

    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public String registerUser(String[] arguments) {
        final int argsLength = arguments.length;

        final String email = argsLength > 1 ? arguments[1] : "";
        final String password = argsLength > 2 ? arguments[2] : "";
        final String confirmPassword = argsLength > 3 ? arguments[3] : "";
        final String fullName = argsLength > 4 ? arguments[4] : "";

        RegisterUserDTO userRegisterDto ;

        try{
            userRegisterDto = new RegisterUserDTO(email,password,confirmPassword,fullName);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }

        boolean doesUserExist = this.userRepository.existsByEmail(userRegisterDto.getEmail());

        if(doesUserExist){
            return USER_ALREADY_EXISTS;
        }


        User mappedUser = mapper.map(userRegisterDto, User.class);

        if(this.userRepository.count() == 0){
            mappedUser.setIsAdmin(true);
        }else{
            mappedUser.setIsAdmin(false);
        }
        this.userRepository.save(mappedUser);

        return String.format(SUCCESSFULLY_REGISTER,mappedUser.getFullName());
    }

    @Override
    public String loginUser(String[] arguments) {
        if(this.loggedInUser !=null){
            return USER_ALREADY_LOGGED_IN;
        }

        final int argsLength = arguments.length;

        final String email = argsLength > 1 ? arguments[1] : "";
        final String password = argsLength > 2 ? arguments[2] : "";


        Optional<User> userToLoggedIn = this.userRepository.findByEmail(email);
        if (userToLoggedIn.isEmpty()) {
            return ErrorMessages.NO_USER_FOUND;
        }

        LogInUserDTO userDTO = new LogInUserDTO(email,password) ;

        try {
           userDTO.validate(userToLoggedIn.get().getPassword());
        }catch (IllegalArgumentException e){
            return  e.getMessage();
        }
        this.loggedInUser = userToLoggedIn.get();

        return String.format(SUCCESSFULLY_LOGGED_IN,userToLoggedIn.get().getFullName());
    }

    @Override
    public String logOut() {
        if(this.loggedInUser == null) return NO_USER_LOGGED_IN;

        String fullName = this.loggedInUser.getFullName();
        this.loggedInUser = null;

        return String.format(SUCCESSFULLY_LOGGED_Out,fullName);
    }

    @Override
    public boolean isLoggedUserAdmin() {
        return this.loggedInUser != null && this.loggedInUser.getIsAdmin();
    }
}
