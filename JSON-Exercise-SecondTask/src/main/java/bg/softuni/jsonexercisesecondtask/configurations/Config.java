package bg.softuni.jsonexercisesecondtask.configurations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class Config {
    @Bean
    public Scanner createScanner(){
        return new Scanner(System.in);
    }

    @Bean
    public ModelMapper createMapper(){
        return new ModelMapper();
    }

    @Bean
    public Gson createGson(){
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
