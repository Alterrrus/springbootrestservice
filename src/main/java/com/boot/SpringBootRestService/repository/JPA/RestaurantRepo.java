package com.boot.SpringBootRestService.repository.JPA;



import com.boot.SpringBootRestService.model.Restaurant;

import java.util.List;

public interface RestaurantRepo {

    List<Restaurant> getAll();
    default List<Restaurant>findAllWithVoteListByIdNotNull(){throw new UnsupportedOperationException();}

    Restaurant save(Restaurant restaurant);

    Restaurant get(Integer id);

    boolean delete(Integer id);

    default List<Restaurant> findAllWithDishListByIdNotNull(){throw new UnsupportedOperationException();}
}
