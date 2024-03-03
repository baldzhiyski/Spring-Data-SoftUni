package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Ingredient;
import bg.softuni.advanced_query_lab.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{
    private IngredientsRepository ingredientsRepository;

    @Autowired
    public IngredientServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public List<Ingredient> getAllIngredientsByGivenFirstLetter(String givenName) {
        return this.ingredientsRepository.findAllByNameStartingWith(givenName).get();
    }

    @Override
    public List<Ingredient> getAllIngredientsWithNameIn(List<String> names) {
        return this.ingredientsRepository.findAllByNameIn(names).get();
    }

    @Override
    @Transactional
    public void deleteAllByGivenName(String name) {
        this.ingredientsRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updateAllPricesBy(BigDecimal price) {
        this.ingredientsRepository.updateAllPrice(price);
    }

    @Override
    @Transactional
    public void updateAllPriceBy10PercentWithNameIn(List<String> names) {
        this.ingredientsRepository.updateAllPriceWhereNameIn(names);
    }
}
