package com.myrating.restaurantRatingSystem.dto;

import com.myrating.restaurantRatingSystem.enums.TypeKitchen;

public record RestaurantRequestDTO(
        String name,
        String description,
        TypeKitchen typeKitchen,
        int midCheck
) {}