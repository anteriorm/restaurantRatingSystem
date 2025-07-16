package com.myrating.restaurantRatingSystem.dto;

import com.myrating.restaurantRatingSystem.enums.TypeKitchen;

import java.math.BigDecimal;

public record RestaurantResponseDTO (
        Long id,
        String name,
        String description,
        TypeKitchen typeKitchen,
        int midCheck,
        BigDecimal rating
) {}
