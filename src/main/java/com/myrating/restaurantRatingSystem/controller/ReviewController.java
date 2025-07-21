package com.myrating.restaurantRatingSystem.controller;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{userId}/{restaurantId}")
    public ReviewResponseDTO getByIdRatings(@PathVariable Long userId, @PathVariable Long restaurantId){
        return ratingService.findById(userId, restaurantId);
    }

    @PostMapping
    public void addRating(@Valid @RequestBody ReviewRequestDTO dto) {
        ratingService.save(dto);
    }

    @DeleteMapping("/{userId}/{restaurantId}")
    public void removeRating(@PathVariable Long userId, @PathVariable Long restaurantId) {
        ratingService.remove(userId, restaurantId);
    }

    @GetMapping("/paged")
    public Page<ReviewResponseDTO> getPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "rating") String sortBy,
            @RequestParam(defaultValue = "false") boolean asc
    ) {
        return ratingService.findAllPaged(page, size, sortBy, asc);
    }
}
