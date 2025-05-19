package com.lm2a.tacosonline.data;

import com.lm2a.tacosonline.model.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
}
