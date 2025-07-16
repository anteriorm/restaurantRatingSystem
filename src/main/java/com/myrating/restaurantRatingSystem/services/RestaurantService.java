package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public void save(RestaurantRequestDTO dto){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.name());
        restaurant.setDescription(dto.description());
        restaurant.setTypeKitchen(dto.typeKitchen());
        restaurant.setMidCheck(dto.midCheck());
        restaurantRepository.save(restaurant);
    }

    public void remove(Long id){
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant != null) {
            restaurantRepository.remove(restaurant);
        }
    }

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurant -> new RestaurantResponseDTO(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getDescription(),
                        restaurant.getTypeKitchen(),
                        restaurant.getMidCheck(),
                        restaurant.getRating()))
                .toList();
    }
}
