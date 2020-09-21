package com.boot.SpringBootRestService.web;


import com.boot.SpringBootRestService.model.Restaurant;
import com.boot.SpringBootRestService.service.RestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnonymousController {
    private final RestService restService;

    public AnonymousController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping()
    public List<Restaurant> getAllRestaurantWithDishAndVote() {
        return restService.findAllWithDishAndVote();
    }

}
