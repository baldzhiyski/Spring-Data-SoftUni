package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.domain.dtos.UserRegisterDTO;
import bg.softuni.mvc_project.domain.entity.User;
import bg.softuni.mvc_project.repositories.UserRepository;
import bg.softuni.mvc_project.services.UserService;
import bg.softuni.mvc_project.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    private ValidationUtils validationUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidationUtils validationUtils) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean register(UserRegisterDTO registerDTO) {
        boolean isValid = this.validationUtils.isValid(registerDTO);

        if(!isValid){
            return false;
        }
        if (this.userRepository.findFirstByEmail(registerDTO.getEmail()).isPresent()) {
            return false;
        }
        User user = this.mapper.map(registerDTO, User.class);
        this.userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public boolean logIn(UserRegisterDTO registerDTO) {
        boolean isValid = this.validationUtils.isValid(registerDTO);

        if(!isValid){
            return false;
        }
        if(this.userRepository.findFirstByUsername(registerDTO.getUsername()).isPresent()) return false;

        return false;
    }
}
