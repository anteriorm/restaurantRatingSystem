package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.RatingId;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.exception.BadRequestException;
import com.myrating.restaurantRatingSystem.exception.ResourceNotFoundException;
import com.myrating.restaurantRatingSystem.mappers.ReviewMapper;
import com.myrating.restaurantRatingSystem.repositories.RatingRepository;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + dto.idUser()));
        Restaurant restaurant = restaurantRepository.findById(dto.idRestaurant())
                .orElseThrow(() -> new ResourceNotFoundException("Ресторан не найден: " + dto.idRestaurant()));
        if (ratingRepository.existsById(new RatingId(user.getId(), restaurant.getId()))) {
            throw new BadRequestException("Пользователь уже оставил отзыв об этом ресторане");
        }
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
        } else {
            throw new ResourceNotFoundException("Отзыв пользователя не найден " + userId + " и ресторан " + restaurantId);
        }
    }

    public Page<ReviewResponseDTO> findAllPaged(int page, int size, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ratingRepository.findAll(pageable).map(reviewMapper::toDto);
    }

    public ReviewResponseDTO findById(Long userId, Long restaurantId) {
        RatingId ratingId = new RatingId(userId, restaurantId);
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException(
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
