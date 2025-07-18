package com.myrating.restaurantRatingSystem.controller;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final RatingService ratingService;

    public ReviewController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<ReviewResponseDTO> getAllRatings() {
        return ratingService.findAll();
    }

    @GetMapping("/{idUser}/{idRestaurant}")
    public Rating getByIdRatings(@PathVariable Long idUser, @PathVariable Long idRestaurant){
        return ratingService.findById(idUser, idRestaurant);
    }

    @PostMapping
    public void addRating(@Valid @RequestBody ReviewRequestDTO dto) {
        ratingService.save(dto);
    }

    @DeleteMapping("/{idUser}/{idRestaurant}")
    public void removeRating(@PathVariable Long idUser,@PathVariable Long idRestaurant) {
        ratingService.remove(idUser, idRestaurant);
    }
}
