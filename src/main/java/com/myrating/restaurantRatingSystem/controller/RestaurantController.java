package com.myrating.restaurantRatingSystem.controller;

import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/min-rating")
    public List<RestaurantResponseDTO> getByMinRating(@RequestParam BigDecimal minRating) {
        return restaurantService.findByMinRating(minRating);
    }

    @GetMapping("/min-rating-query")
    public List<RestaurantResponseDTO> getByMinRatingQuery(@RequestParam BigDecimal minRating) {
        return restaurantService.findByMinRatingQuery(minRating);
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAllUsers(){
        return restaurantService.findAll();
    }

    @PostMapping
    public void addRestaurant(@Valid @RequestBody RestaurantRequestDTO restaurantRequestDTO){
        restaurantService.save(restaurantRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void removeRestaurant(@PathVariable Long id){
        restaurantService.remove(id);
    }



}
