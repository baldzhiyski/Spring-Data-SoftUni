package bg.softuni.mvc_project.web.controllers;

import bg.softuni.mvc_project.domain.dtos.UserLoginDTO;
import bg.softuni.mvc_project.domain.dtos.UserRegisterDTO;
import bg.softuni.mvc_project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController extends BaseController{
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public ModelAndView getLoginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");


        return modelAndView;
    }

    @PostMapping("/users/login")
    public ModelAndView doLoginUser(@Valid UserLoginDTO loginDTO, BindingResult bindingResult){
        if (loginDTO.getUsername().equals("admin") &&
                loginDTO.getPassword().equals("secure")) {

            return super.redirect("/home");
        }
        bindingResult.rejectValue("password", "error.login", "Invalid username or password");
        List<String> errors = bindingResult
                .getAllErrors()
                .stream()
                .map(e -> e.getObjectName() + " " + e.getDefaultMessage())
                .toList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        modelAndView.addObject("errors", errors);

        return modelAndView;
    }

    @GetMapping("users/register")
    public ModelAndView getRegisterPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/register");


        return modelAndView;
    }

    @PostMapping("users/register")
    public ModelAndView registerUser( @Valid UserRegisterDTO registerDTO){
        boolean success = userService.register(registerDTO);

        if (success) {
            // Redirect to the login page after successful registration
            return super.redirect("/users/login");
        }

        // Redirect back to the registration page if registration fails
        return super.redirect("/users/register");
    }
}
