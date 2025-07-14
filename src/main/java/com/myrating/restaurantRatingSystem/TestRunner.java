package com.myrating.restaurantRatingSystem;

import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.TypeKitchen;
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

        Users user3 = new Users(3L, "Катя", 25, "Женский");
        Users user4 = new Users(4L, "Иван", 40, "Мужской");
        userService.save(user3);
        userService.save(user4);

        Restaurant rest3 = new Restaurant(3L, "Суши-бар", "Японская кухня", TypeKitchen.JAPANESE, 1500, BigDecimal.valueOf(5.0));
        Restaurant rest4 = new Restaurant(4L, "Пиццерия", "Итальянская кухня", TypeKitchen.ITALIAN, 1200, BigDecimal.valueOf(4.0));
        restaurantService.save(rest3);
        restaurantService.save(rest4);


        ratingService.save(new Rating(3L, 3L, 5, "Очень вкусно!"));
        ratingService.save(new Rating(4L, 4L, 4, "Хорошо, но дорого"));


        System.out.println("Все пользователи:");
        for (Users user : userService.findAll()) {
            System.out.println(user);
        }

        System.out.println("Все рестораны:");
        for (Restaurant restaurant : restaurantService.findAll()) {
            System.out.println(restaurant);
        }

        System.out.println("Все оценки:");
        for (Rating rating : ratingService.findAll()) {
            System.out.println(rating);
        }
    }
}