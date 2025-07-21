package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.mappers.RestaurantMapper;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import com.myrating.restaurantRatingSystem.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void testSave() {
        RestaurantRequestDTO dto = mock(RestaurantRequestDTO.class);
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantMapper.toEntity(dto)).thenReturn(restaurant);

        restaurantService.save(dto);

        verify(restaurantMapper).toEntity(dto);
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void testRemove() {
        Long id = 1L;
        when(restaurantRepository.existsById(id)).thenReturn(true);
        restaurantService.remove(id);
        verify(restaurantRepository).deleteById(id);
    }

    @Test
    void testFindAll() {
        Restaurant restaurant = mock(Restaurant.class);
        RestaurantResponseDTO dto = mock(RestaurantResponseDTO.class);
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(restaurantMapper.toDto(restaurant)).thenReturn(dto);

        List<RestaurantResponseDTO> result = restaurantService.findAll();

        assertEquals(1, result.size());
        verify(restaurantRepository).findAll();
        verify(restaurantMapper).toDto(restaurant);
    }

    @Test
    void testFindByMinRating() {
        BigDecimal minRating = BigDecimal.valueOf(4.0);
        Restaurant restaurant = mock(Restaurant.class);
        RestaurantResponseDTO dto = mock(RestaurantResponseDTO.class);
        when(restaurantRepository.findByRatingGreaterThanEqual(minRating)).thenReturn(List.of(restaurant));
        when(restaurantMapper.toDto(restaurant)).thenReturn(dto);

        List<RestaurantResponseDTO> result = restaurantService.findByMinRating(minRating);

        assertEquals(1, result.size());
        verify(restaurantRepository).findByRatingGreaterThanEqual(minRating);
        verify(restaurantMapper).toDto(restaurant);
    }
}
