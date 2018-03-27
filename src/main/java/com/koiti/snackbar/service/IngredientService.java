package com.koiti.snackbar.service;

import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.repository.IngredientRepository;
import com.koiti.snackbar.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients() {
        return ingredientRepository.getIngredients();
    }

    /**
     * Find and return ingredient value at ingredients list
     * @param ingredient
     * @param ingredients
     * @return value
     */
    public Double getIngredientValue(final String ingredient, List<Ingredient> ingredients ) {
        Optional<Ingredient> ingredientWithValue = ingredients.stream().filter(i -> i.getName().equals(ingredient)).findFirst();
        if (ingredientWithValue.isPresent()) {
            return ingredientWithValue.get().getValue();
        }
        return null;
    }
}
