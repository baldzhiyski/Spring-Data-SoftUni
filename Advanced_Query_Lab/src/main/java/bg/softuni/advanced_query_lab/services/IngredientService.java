package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    List<Ingredient> getAllIngredientsByGivenFirstLetter(String givenName);

    List<Ingredient> getAllIngredientsWithNameIn(List<String> names);

    void deleteAllByGivenName(String name);

    void updateAllPricesBy(BigDecimal price);

}
