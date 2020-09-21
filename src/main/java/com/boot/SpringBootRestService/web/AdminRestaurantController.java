package com.boot.SpringBootRestService.web;


import com.boot.SpringBootRestService.model.Dish;
import com.boot.SpringBootRestService.model.Restaurant;
import com.boot.SpringBootRestService.model.Vote;
import com.boot.SpringBootRestService.service.DishService;
import com.boot.SpringBootRestService.service.RestService;
import com.boot.SpringBootRestService.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    private final RestService service;
    private final DishService dishService;
    private final VoteService voteService;


    public AdminRestaurantController(RestService service, DishService dishService, VoteService voteService) {
        this.service = service;
        this.dishService = dishService;
        this.voteService = voteService;
    }

    @GetMapping()
    public List<Restaurant> getAllRestaurantWithDishAndVote() {
        return service.findAllWithDishAndVote();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping(value = "/{restaurantId}/dishes")
    public List<Dish> getAllRestaurantDish(@PathVariable int restaurantId) {
        return dishService.getAll(restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        service.update(restaurant,id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/restaurants" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @GetMapping(value = "/{restaurantId}/dishes/{id}")
    public Dish getDish(@PathVariable int restaurantId,@PathVariable int id){
        return dishService.get(id,restaurantId);
    }


    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        dishService.update(dish, restaurantId, id);
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish, @PathVariable int restaurantId) {
        Dish created = dishService.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/restaurants/" + restaurantId + "/dishes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/{restaurantId}/votes")
    public List<Vote> getAllRestaurantVote(@PathVariable int restaurantId) {
        return voteService.getAll(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        dishService.delete(id,restaurantId);
    }


}
