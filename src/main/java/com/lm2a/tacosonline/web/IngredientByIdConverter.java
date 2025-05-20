package com.lm2a.tacosonline.web;

import com.lm2a.tacosonline.data.IngredientRepository;
import com.lm2a.tacosonline.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientByIdConverter  implements Converter<String, Ingredient> {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String source) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(source);
        return ingredient.isPresent()? ingredient.get() : null;
    }
}
