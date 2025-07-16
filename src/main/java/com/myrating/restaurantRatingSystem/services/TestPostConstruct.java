package com.myrating.restaurantRatingSystem.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TestPostConstruct {
    private final RatingService ratingService;
    private final UserService userService;
    private final RestaurantService restaurantService;


    @PostConstruct
    public void init(){
    }
}
