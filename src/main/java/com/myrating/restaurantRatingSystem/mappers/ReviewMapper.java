package com.myrating.restaurantRatingSystem.mappers;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Rating toEntity(ReviewRequestDTO dto);
    @Mapping(target = "idUser", source = "user.id")
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    ReviewResponseDTO toDto(Rating entity);
}
