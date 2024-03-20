package bg.softuni.mvc_project.services;

import bg.softuni.mvc_project.domain.dtos.UserRegisterDTO;

public interface UserService {
    boolean register(UserRegisterDTO registerDTO);

    boolean logIn(UserRegisterDTO registerDTO);

}
