package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.TypeKitchen;
import com.myrating.restaurantRatingSystem.entities.Users;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestPostConstruct {
    private final RatingService ratingService;
    private final UserService userService;
    private final RestaurantService restaurantService;


    @PostConstruct
    public void init(){
        Users user1 = new Users(1L,"Миша",30,"Мужской");
        userService.save(user1);

        Restaurant restaurant1 = new Restaurant(1L, "Вкусно", null, TypeKitchen.ITALIAN, 1000, BigDecimal.valueOf(1.0));
        restaurantService.save(restaurant1);

        Rating rating1 = new Rating(1L, 1L, 1, null);
        ratingService.save(rating1);

        Users user2 = new Users(2L,"Паша",20,"Мужской");
        userService.save(user2);

        Restaurant restaurant2 = new Restaurant(2L, "неВкусно", null, TypeKitchen.EUROPEAN, 2000, BigDecimal.valueOf(10.0));
        restaurantService.save(restaurant2);

        Rating rating2 = new Rating(2L, 2L, 10, null);
        ratingService.save(rating2);

        Rating rating3 = new Rating(2L, 2L, 10, null);
        ratingService.remove(rating3);

        List<Rating> ratings = ratingService.findAll();

        System.out.println("Список Rating:");
        for (Rating rating : ratings) {
            System.out.println(rating);
        }

        List<Users> users = userService.findAll();

        System.out.println("Список Users:");
        for (Users user : users) {
            System.out.println(user);
        }

        List<Restaurant> restaurants = restaurantService.findAll();

        System.out.println("Список Restaurants:");
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }
}
