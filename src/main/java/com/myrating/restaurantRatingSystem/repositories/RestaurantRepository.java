package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByRatingGreaterThanEqual(BigDecimal rating);
    @Query("SELECT r FROM Restaurant r WHERE r.rating >= :minRating")
    List<Restaurant> findRestaurantsWithMinRating(@Param("minRating") BigDecimal minRating);
}