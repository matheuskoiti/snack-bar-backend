package com.koiti.snackbar.service;

import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.repository.IngredientRepository;
import com.koiti.snackbar.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients() {
        return ingredientRepository.getIngredients();
    }
}
