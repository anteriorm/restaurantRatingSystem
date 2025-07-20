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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(ReviewRequestDTO dto) {
        Users user = userRepository.findById(dto.idUser())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.idUser()));
        Restaurant restaurant = restaurantRepository.findById(dto.idRestaurant())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + dto.idRestaurant()));
        Rating rating = reviewMapper.toEntity(dto);
        rating.setUser(user);
        rating.setRestaurant(restaurant);
        rating.setId(new RatingId(user.getId(), restaurant.getId()));
        ratingRepository.save(rating);
        recalculateRestaurantRating(restaurant.getId());
    }

    public void remove(Long userId, Long restaurantId) {
        RatingId ratingId = new RatingId(userId, restaurantId);
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);
        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();
            Long restId = rating.getRestaurant().getId();
            ratingRepository.deleteById(ratingId);
            recalculateRestaurantRating(restId);
        }
    }

    public ReviewResponseDTO findById(Long userId, Long restaurantId) {
        RatingId ratingId = new RatingId(userId, restaurantId);
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Отзыв не найден для пользователя " + userId + " и ресторана " + restaurantId));
        return reviewMapper.toDto(rating);
    }

    public List<ReviewResponseDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            List<Rating> allRatings = ratingRepository.findByRestaurantId(restaurantId);
            int sum = allRatings.stream().mapToInt(Rating::getRating).sum();
            int count = allRatings.size();
            if (count > 0) {
                BigDecimal avg = BigDecimal.valueOf((double) sum / count).setScale(2, RoundingMode.HALF_UP);
                restaurant.setRating(avg);
            } else {
                restaurant.setRating(BigDecimal.ZERO);
            }
            restaurantRepository.save(restaurant);
        }
    }
}
