package bg.softuni.mapping_objects_lab.config;

import bg.softuni.mapping_objects_lab.JsonTestMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
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
    @Bean
    public Gson createGson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("YYYY-MM-DD")
                .registerTypeAdapter(LocalDate.class, new JsonTestMain.LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }
}
