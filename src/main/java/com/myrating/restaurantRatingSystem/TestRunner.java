package com.myrating.restaurantRatingSystem;

import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.enums.TypeKitchen;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.services.RatingService;
import com.myrating.restaurantRatingSystem.services.RestaurantService;
import com.myrating.restaurantRatingSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    @Override
    public void run(String... args) {

    }
}