package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Shampoo;

import java.util.List;

public interface ShampooService {

    List<Shampoo> getAllShampoosBySize(String size);
}
