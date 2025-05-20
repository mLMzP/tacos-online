package com.lm2a.tacosonline.data;

import com.lm2a.tacosonline.model.Order;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
