package com.myrating.restaurantRatingSystem.dto;

public record ReviewRequestDTO(
        Long idUser,
        Long idRestaurant,
        int rating,
        String feedback
) {}
