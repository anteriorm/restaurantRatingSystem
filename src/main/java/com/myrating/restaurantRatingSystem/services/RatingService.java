package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.repositories.RatingRepository;
import com.myrating.restaurantRatingSystem.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    private final RestaurantRepository restaurantRepository;

    public void save(ReviewRequestDTO dto) {
        Rating rating = new Rating();
        rating.setIdUser(dto.idUser());
        rating.setIdRestaurant(dto.idRestaurant());
        rating.setRating(dto.rating());
        rating.setFeedback(dto.feedback());
        ratingRepository.save(rating);
        recalculateRestaurantRating(rating.getIdRestaurant());
    }

    public void remove(Long idUser, Long idRestaurant) {
        Rating rating = ratingRepository.findById(idUser, idRestaurant);
        if (rating != null) {
            ratingRepository.remove(rating);
            recalculateRestaurantRating(rating.getIdRestaurant());
        }
    }

    public Rating findById(Long idUser, Long idRestaurant) {
        return ratingRepository.findById(idUser, idRestaurant);
    }

    public List<ReviewResponseDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(r -> new ReviewResponseDTO(
                        r.getIdUser(),
                        r.getIdRestaurant(),
                        r.getRating(),
                        r.getFeedback()
                ))
                .toList();
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findAll().stream()
                .filter(r -> r.getId().equals(restaurantId))
                .findFirst()
                .orElse(null);

        if (restaurant != null) {
            List<Rating> allRatings = ratingRepository.findAll();
            int sum = 0;
            int count = 0;
            for (Rating r : allRatings) {
                if (r.getIdRestaurant().equals(restaurantId)) {
                    sum += r.getRating();
                    count++;
                }
            }
            if (count > 0) {
                BigDecimal avg = BigDecimal.valueOf((double) sum / count).setScale(2, RoundingMode.HALF_UP);
                restaurant.setRating(avg);
            } else {
                restaurant.setRating(BigDecimal.ZERO);
            }
        }
    }
}
