package com.myrating.restaurantRatingSystem.mappers;

import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantRequestDTO dto);
    RestaurantResponseDTO toDto(Restaurant entity);
}