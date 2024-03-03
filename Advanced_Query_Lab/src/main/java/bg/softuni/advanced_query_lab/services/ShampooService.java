package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Shampoo;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<Shampoo> getAllShampoosBySize(String size);

    List<Shampoo> getAllShampoosBySizeOrLabel(String size,long labelId);

    List<Shampoo> getAllShampoosWithGreaterPriceThan(BigDecimal price);

    int getNumberOfShampoosWithPriceLessThan(BigDecimal price);

    List<String> getAllShampoosWhereIngredientsNameIn(List<String> names);

    List<Shampoo> getAllShampoosNamesWhereIngredientsCountIs(int count);


}
