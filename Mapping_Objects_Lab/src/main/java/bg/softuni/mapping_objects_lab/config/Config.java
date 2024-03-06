package bg.softuni.mapping_objects_lab.config;

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
        return  new ModelMapper();
    }
}
