package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();

    public void save(Rating rating){
        ratings.add(rating);
    }

    public void remove(Rating rating){
        ratings.remove(rating);
    }

    public List<Rating> findAll() {
        return new ArrayList<>(ratings);
    }

    public Rating findById(Long id) {
        for (Rating rating : ratings) {
            if (rating.getIdUser().equals(id)) {
                return rating;
            }
        }
        return null;
    }
}
