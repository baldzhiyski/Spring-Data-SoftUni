package bg.softuni.mvc_project.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController  extends BaseController{

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");


        return modelAndView;
    }
    @GetMapping("/home")
    public ModelAndView homeMethod(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");


        return modelAndView;
    }

}

