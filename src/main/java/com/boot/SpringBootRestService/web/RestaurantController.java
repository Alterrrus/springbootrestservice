package com.boot.SpringBootRestService.web;

import com.boot.SpringBootRestService.model.Dish;
import com.boot.SpringBootRestService.model.Restaurant;
import com.boot.SpringBootRestService.model.Vote;
import com.boot.SpringBootRestService.service.DishService;
import com.boot.SpringBootRestService.service.RestService;
import com.boot.SpringBootRestService.service.VoteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final DishService dishService;
    private final RestService restService;
    private final VoteService voteService;

    public RestaurantController(DishService dishService, RestService restService, VoteService voteService) {
        this.dishService = dishService;
        this.restService = restService;
        this.voteService = voteService;
    }

    @PutMapping(value = "/{restaurantId}/votes")
    public ResponseEntity<Vote> addVote(@PathVariable int restaurantId) {
         Vote created=voteService.save(restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/restaurants/" + restaurantId + "/votes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);

    }
    @GetMapping(value = "/{restaurantId}/votes")
    public List<Vote> getAllRestaurantVote(@PathVariable int restaurantId) {
        return voteService.getAll(restaurantId);}

    @GetMapping()
    public List<Restaurant> getAllRestaurantWithDishAndVote() {
        return restService.findAllWithDishAndVote();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable int id) {
        return restService.get(id);
    }
    @GetMapping(value = "/{restaurantId}/dishes")
    public List<Dish> getAllRestaurantDish(@PathVariable int restaurantId) {
        return dishService.getAll(restaurantId);
    }

}
