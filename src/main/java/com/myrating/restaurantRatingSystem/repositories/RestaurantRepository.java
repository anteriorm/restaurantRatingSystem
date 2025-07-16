package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.Users;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();
    private static long COUNTER = 1;

    public void save(Restaurant restaurant){
        if (restaurant.getId() == null) {
            restaurant.setId(COUNTER++);
        }
        restaurants.add(restaurant);
    }

    public void remove(Restaurant restaurant){
        restaurants.remove(restaurant);
    }

    public List<Restaurant> findAll() {
        return new ArrayList<>(restaurants);
    }

    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
