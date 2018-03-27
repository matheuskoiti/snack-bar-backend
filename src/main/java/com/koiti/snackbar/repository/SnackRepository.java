package com.koiti.snackbar.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SnackRepository {

    @Value("classpath:data/snacks.json")
    private Resource resourceSnacks;

    @Autowired
    private IngredientService ingredientService;

    /**
     * Read and parse snacks.json file that contains all snacks of the Snack Bar
     * Read and parse ingredients.json file that contains ingredient's value
     * @return snacks
     */
    public List<Snack> getSnacks() {
        List<Snack> snacks = new ArrayList<Snack>();
        JsonObject jsonObject;
        Gson gson = new Gson();
        List<Ingredient> ingredients = ingredientService.getIngredients();

        try {
            jsonObject = new JsonParser().parse(new FileReader(resourceSnacks.getFile())).getAsJsonObject();

            for(JsonElement snackItem : jsonObject.getAsJsonArray("snacks")){
                Snack snack = gson.fromJson(snackItem.getAsJsonObject(), Snack.class);
                for(Ingredient ingredient : snack.getIngredients()) {
                    ingredient.setValue(ingredientService.getIngredientValue(ingredient.getName(), ingredients));
                }
                snacks.add(snack);
            }
            return snacks;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading/parsing snacks.json file");
        }
    }
}
