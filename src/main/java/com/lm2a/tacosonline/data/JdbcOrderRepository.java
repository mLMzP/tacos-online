package com.lm2a.tacosonline.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm2a.tacosonline.model.Order;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Taco_Order_Tacos");
        objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        for(Taco taco : order.getTacos()) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order) {
        Map<String, Object> map = objectMapper.convertValue(order, Map.class);
        map.put("placedAt", order.getPlacedAt());
        Number number = orderInserter.executeAndReturnKey(map);
        return number.longValue();
    }


    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("taco", taco.getId());
        map.put("tacoOrder", orderId);
        orderTacoInserter.execute(map);
    }

}
