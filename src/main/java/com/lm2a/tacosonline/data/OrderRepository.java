package com.lm2a.tacosonline.data;

import com.lm2a.tacosonline.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
