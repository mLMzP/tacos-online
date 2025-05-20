package com.lm2a.tacosonline.data;

import com.lm2a.tacosonline.model.Ingredient;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
