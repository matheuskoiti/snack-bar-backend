package com.koiti.snackbar.service;

import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    /**
     * Get all ingredients from the snack bar
     * @return
     */
    public List<Ingredient> getIngredients() {
        return ingredientRepository.getIngredients();
    }

    /**
     * Find the ingredient name in the ingredient list
     * @param ingredient
     * @param ingredients
     * @return the ingredient value
     */
    public Double getIngredientValue(final String ingredient, List<Ingredient> ingredients ) {
        Optional<Ingredient> ingredientWithValue = ingredients.stream().filter(i -> i.getName().equals(ingredient)).findFirst();
        if (ingredientWithValue.isPresent()) {
            return ingredientWithValue.get().getValue();
        }
        return null;
    }
}
