package com.boot.SpringBootRestService.repository.JpaImpl;


import com.boot.SpringBootRestService.model.Dish;
import com.boot.SpringBootRestService.repository.JPA.DishRepo;
import com.boot.SpringBootRestService.repository.extendDataJpa.DishJPA;
import com.boot.SpringBootRestService.repository.extendDataJpa.RestaurantJPA;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DishJpaImpl implements DishRepo {

    private final DishJPA dishJPA;
    private final RestaurantJPA restaurantJPA;

    public DishJpaImpl(DishJPA dishJPA, RestaurantJPA restaurantJPA) {
        this.dishJPA = dishJPA;
        this.restaurantJPA = restaurantJPA;
    }

    @Override
    public List<Dish> getAll(Integer restaurantId) {
        return dishJPA.getAll(restaurantId);
    }

    @Override
    @Transactional
    public Dish save(Dish dish, Integer restaurantId) {
        if (!dish.isNew()&&get(dish.getId(),restaurantId)==null){return null;}
        dish.setRestaurant(restaurantJPA.getOne(restaurantId));
        return dishJPA.save(dish);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return dishJPA.findById(id).filter(dish -> dish.getRestaurant().getId()==(restaurantId)).orElse(null);
    }

    @Override
    public boolean delete(Integer id, Integer restaurantId) {
        return dishJPA.delete(id,restaurantId)!=0;
    }
}
