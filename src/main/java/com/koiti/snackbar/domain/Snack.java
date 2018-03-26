package com.koiti.snackbar.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Snack {
    private String name;
    private List<Ingredient> ingredients;
}
