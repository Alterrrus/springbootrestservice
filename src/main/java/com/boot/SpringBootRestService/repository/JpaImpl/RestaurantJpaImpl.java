package com.boot.SpringBootRestService.repository.JpaImpl;


import com.boot.SpringBootRestService.model.Restaurant;
import com.boot.SpringBootRestService.repository.JPA.RestaurantRepo;
import com.boot.SpringBootRestService.repository.extendDataJpa.RestaurantJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantJpaImpl implements RestaurantRepo {

    private final RestaurantJPA restRepo;

    @Autowired
    public RestaurantJpaImpl(RestaurantJPA restRepo) {
        this.restRepo = restRepo;
    }
    @Override
    public List<Restaurant>getAll(){
        return restRepo.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }



    @Override
    public Restaurant save(Restaurant restaurant) {
        return restRepo.save(restaurant);
    }

    @Override
    public Restaurant get(Integer id) {
        return restRepo.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return restRepo.delete(id)!=0;
    }

    @Override
    public List<Restaurant> findAllWithDishListByIdNotNull(){
     return restRepo.findAllWithDishListByIdNotNull();
    }

    @Override
    public List<Restaurant> findAllWithVoteListByIdNotNull(){
        return restRepo.findAllWithVoteListByIdNotNull();
    }

}
