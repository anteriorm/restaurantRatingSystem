package com.myrating.restaurantRatingSystem.dto;

import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @NotNull
        Long idUser,
        @NotNull
        Long idRestaurant,
        @NotNull
        int rating,
        String feedback
) {}
