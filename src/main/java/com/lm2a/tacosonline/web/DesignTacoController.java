package com.lm2a.tacosonline.web;


import com.lm2a.tacosonline.data.IngredientRepository;
import com.lm2a.tacosonline.data.TacoRepository;
import com.lm2a.tacosonline.model.Ingredient;
import com.lm2a.tacosonline.model.Ingredient.Type;
import com.lm2a.tacosonline.model.Order;
import com.lm2a.tacosonline.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private IngredientRepository ingredientRepo;

    private TacoRepository tacoRepo;

    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    @GetMapping
    public String showDesignForm(Model model){
//        int x = 0;
//        int y = 1/x;
        fillIngredients(model);
        //model.addAttribute("tktn", new Taco());
        return "design";
    }

    @ModelAttribute(name="tktn")
    public Taco taco(){
        log.info("Iniciando Taco");
        return new Taco();
    }

    @ModelAttribute
    public Order order(){
        log.info("Iniciando Taco");
        return new Order();
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute(name="tktn") Taco design, Errors errors, Model model, @ModelAttribute Order order){
        if(errors.hasErrors()){
            fillIngredients(model);
            return "design";
        }

        Taco saved = tacoRepo.save(design);
        log.info("Processing Design Taco: "+saved);

        order.addDesign(design);

        return "redirect:/orders/current";
    }


    public void fillIngredients(Model model){
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
}
