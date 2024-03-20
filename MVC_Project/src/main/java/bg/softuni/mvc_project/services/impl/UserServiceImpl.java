package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.domain.dtos.UserRegisterDTO;
import bg.softuni.mvc_project.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean register(UserRegisterDTO registerDTO) {
        return false;
    }

    @Override
    public boolean logIn(UserRegisterDTO registerDTO) {
        return false;
    }
}
