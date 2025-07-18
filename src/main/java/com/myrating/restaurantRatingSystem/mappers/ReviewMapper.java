package com.myrating.restaurantRatingSystem.mappers;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Rating toEntity(ReviewRequestDTO dto);
    ReviewResponseDTO toDto(Rating entity);
}
