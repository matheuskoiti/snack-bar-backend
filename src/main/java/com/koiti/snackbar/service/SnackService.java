package com.koiti.snackbar.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.domain.response.OrderValueResponse;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.repository.IngredientRepository;
import com.koiti.snackbar.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SnackService {

    @Autowired
    SnackRepository snackRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Value("classpath:data/snacks.json")
    private Resource resourceSnacks;

    /**
     *
     * @return
     */
    public List<Snack> getSnacks() {
        return snackRepository.getSnacks();
    }

    /**
     *
     * @return
     */
    public double getSnacksValue(List<String> snacks, List<String> quantities) {
        List<Snack> allSnacks = getSnacks();
        double snackPrice = 0, finalValue = 0;
        int index = -1;
        // Flag and counters for promotions
        boolean hasLettuce = false, hasBacon = false;
        int countMeat = 0, countCheese = 0;
        int discountMeat = 0;
        int discountCheese = 0;

        for (String quantity : quantities) {
            index++;
            if (quantity.isEmpty() || quantity.equals("")) {
                continue;
            }

            int currentQuantity = Integer.parseInt(quantity);
            String currentSnackName = snacks.get(index);
            Snack currentSnack = null;

            // Find current snack
            for (Snack snack : allSnacks) {
                if (snack.getName().equals(currentSnackName)) {
                    currentSnack = snack;
                }
            }

            // Find snack value, adding all ingredient values
            List<Ingredient> ingredients = currentSnack.getIngredients();
            for (Ingredient ingredient : ingredients) {
                snackPrice += ingredient.getValue();

                // Flags for promotions
                if (ingredient.getName().equals("Alface")) {
                    hasLettuce = true;
                }

                if (ingredient.getName().equals("Bacon")) {
                    hasBacon = true;
                }

                if (ingredient.getName().equals("Hamburguer de carne")) {
                    countMeat++;
                }

                if (ingredient.getName().equals("Queijo")) {
                    countCheese++;
                }
            }

            snackPrice = usePromotion(snackPrice, hasBacon, hasLettuce, countMeat, countCheese);

            if (currentQuantity > 0) {
                finalValue += snackPrice * currentQuantity;
            }

            snackPrice = 0;
            countMeat = 0;
            countCheese = 0;
            hasBacon = false;
            hasLettuce = false;
        }

        return finalValue;
    }

    /**
     *
     * @param ingredients
     * @param quantities
     * @return
     */
    public double getCustomSnacksValue(List<String> ingredients, List<String> quantities){
        List<Ingredient> allIngredients = ingredientRepository.getIngredients();
        int index = -1;
        double snackValue = 0;
        // Flag and counters for promotions
        boolean hasLettuce = false, hasBacon = false;
        int countMeat = 0, countCheese = 0;
        int discountMeat = 0;
        int discountCheese = 0;

        for (String quantity : quantities) {
            index++;
            if (quantity.isEmpty() || quantity.equals("")) {
                continue;
            }

            for (Ingredient ingredient : allIngredients) {
                if (ingredient.getName().equals(ingredients.get(index))) {
                    snackValue += (ingredient.getValue()* Integer.parseInt(quantity) );

                    // Flags for promotions
                    if (ingredient.getName().equals("Alface")) {
                        hasLettuce = true;
                    }

                    if (ingredient.getName().equals("Bacon")) {
                        hasBacon = true;
                    }

                    if (ingredient.getName().equals("Hamburguer de carne")) {
                        countMeat = Integer.parseInt(quantity);
                    }

                    if (ingredient.getName().equals("Queijo")) {
                        countCheese = Integer.parseInt(quantity);
                    }
                }
            }
        }

        snackValue = usePromotion(snackValue, hasBacon, hasLettuce, countMeat, countCheese);

        return snackValue;
    }

    /**
     * 
     * @param snackValue
     * @param hasBacon
     * @param hasLettuce
     * @param countMeat
     * @param countCheese
     * @return
     */
    private double usePromotion(double snackValue, boolean hasBacon, boolean hasLettuce, int countMeat, int countCheese) {
        List<Ingredient> allIngredients = ingredientRepository.getIngredients();

        // Verify if light promotion can be applied
        if (hasLettuce && !hasBacon) {
            snackValue = snackValue- (snackValue * 0.1);
        }

        // Verify and apply meat and cheese discounts
        int discountMeat = countMeat/3;
        int discountCheese = countCheese/3;
        double meatPrice = ingredientRepository.getIngredientValue("Hamburguer de carne", allIngredients);
        double cheesePrice = ingredientRepository.getIngredientValue("Queijo", allIngredients);
        snackValue = snackValue - (meatPrice*discountMeat);
        snackValue = snackValue - (cheesePrice*discountCheese);

        return snackValue;
    }

}
