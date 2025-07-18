package com.myrating.restaurantRatingSystem.mappers;

import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserRequestDTO dto);
    UserResponseDTO toDto(Users entity);
}
