package com.myrating.restaurantRatingSystem.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO (
        @NotBlank
        String name,
        @Min(0)
        @Max(10)
        int age,
        String gender
){}
