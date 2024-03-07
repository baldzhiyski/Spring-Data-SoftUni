package bg.softuni.game_store_console_app.config;

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
}
