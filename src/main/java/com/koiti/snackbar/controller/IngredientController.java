package com.koiti.snackbar.controller;

import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    /**
     * Get all ingredients from the snack bar
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Ingredient> getIngredients() {
        return ingredientService.getIngredients();

    }
}
