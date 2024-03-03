package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> getAllIngredientsByGivenFirstLetter(String givenName);

}
