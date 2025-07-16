package com.myrating.restaurantRatingSystem.dto;

public record ReviewResponseDTO(
        Long idUser,
        Long idRestaurant,
        int rating,
        String feedback
) {}
