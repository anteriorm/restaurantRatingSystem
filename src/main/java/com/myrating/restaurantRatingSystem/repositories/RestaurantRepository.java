package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}