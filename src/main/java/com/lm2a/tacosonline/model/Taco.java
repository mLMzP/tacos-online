package com.lm2a.tacosonline.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class Taco {
    @NotNull
    @Size(min = 5, message="El nombre debe tener como minimo 5 caracteres")
    private String name;
    private List<String> ingredients;
}
