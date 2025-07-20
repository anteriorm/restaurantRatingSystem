package com.myrating.restaurantRatingSystem.dto;

import com.myrating.restaurantRatingSystem.enums.TypeKitchen;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequestDTO(
        @NotBlank
        String name,
        String description,
        @NotNull
        TypeKitchen typeKitchen,
        @Min(0)
        int midCheck
) {}