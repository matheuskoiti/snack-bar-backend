package com.koiti.snackbar.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient {
    private String name;
    private Double value;
}
