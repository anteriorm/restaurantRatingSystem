package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.RatingId;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.mappers.ReviewMapper;
import com.myrating.restaurantRatingSystem.repositories.RatingRepository;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import com.myrating.restaurantRatingSystem.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void testSave() {
        ReviewRequestDTO dto = mock(ReviewRequestDTO.class);
        Users user = mock(Users.class);
        Restaurant restaurant = mock(Restaurant.class);
        Rating rating = mock(Rating.class);

        when(dto.idUser()).thenReturn(1L);
        when(dto.idRestaurant()).thenReturn(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));
        when(reviewMapper.toEntity(dto)).thenReturn(rating);

        when(user.getId()).thenReturn(1L);
        when(restaurant.getId()).thenReturn(2L);

        when(ratingRepository.findByRestaurantId(2L)).thenReturn(List.of(rating));

        ratingService.save(dto);

        verify(userRepository).findById(1L);
        verify(restaurantRepository, times(2)).findById(2L);
        verify(reviewMapper).toEntity(dto);
        verify(ratingRepository).save(rating);
    }

    @Test
    void testRemove() {
        Long userId = 1L, restaurantId = 2L;
        Rating rating = mock(Rating.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(ratingRepository.findById(any(RatingId.class))).thenReturn(Optional.of(rating));
        when(rating.getRestaurant()).thenReturn(restaurant);
        when(restaurant.getId()).thenReturn(restaurantId);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(ratingRepository.findByRestaurantId(restaurantId)).thenReturn(List.of());

        ratingService.remove(userId, restaurantId);

        verify(ratingRepository).deleteById(argThat(id ->
                id.getUserId().equals(userId) && id.getRestaurantId().equals(restaurantId)
        ));
    }

    @Test
    void testFindAll() {
        Rating rating = mock(Rating.class);
        ReviewResponseDTO dto = mock(ReviewResponseDTO.class);
        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        when(reviewMapper.toDto(rating)).thenReturn(dto);

        List<ReviewResponseDTO> result = ratingService.findAll();

        assertEquals(1, result.size());
        verify(ratingRepository).findAll();
        verify(reviewMapper).toDto(rating);
    }
}
