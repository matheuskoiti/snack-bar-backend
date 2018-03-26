package com.koiti.snackbar.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koiti.snackbar.domain.Ingredient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {
    @Value("classpath:data/ingredients.json")
    private Resource resourceIngredients;


    /**
     * Read and parse ingredients.json file that contains all ingredients of the Snack Bar
     * @return snacks
     */
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        JsonObject jsonObject;
        Gson gson = new Gson();
        try {
            jsonObject = new JsonParser().parse(new FileReader(resourceIngredients.getFile())).getAsJsonObject();

            for(JsonElement snackItem : jsonObject.getAsJsonArray("ingredients")){
                ingredients.add(gson.fromJson(snackItem.getAsJsonObject(), Ingredient.class));
            }
            return ingredients;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading/parsing ingredients.json file");
        }
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
