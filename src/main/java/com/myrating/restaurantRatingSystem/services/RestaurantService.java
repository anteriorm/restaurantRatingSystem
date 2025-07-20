package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.mappers.RestaurantMapper;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public void save(RestaurantRequestDTO dto){
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurantRepository.save(restaurant);
    }

    public void remove(Long id){
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .toList();
    }
}
