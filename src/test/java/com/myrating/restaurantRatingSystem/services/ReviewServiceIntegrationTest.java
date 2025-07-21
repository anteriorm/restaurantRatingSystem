package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.enums.TypeKitchen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RatingService ratingService;

    @Test
    void saveAndFindReview() {
        // Сначала создаём пользователя и ресторан
        userService.save(new UserRequestDTO("Оценщик", 9, "мужской"));
        restaurantService.save(new RestaurantRequestDTO("Ресторан для отзыва", "Описание", TypeKitchen.ITALIAN, 600));

        Long userId = userService.findAll().stream()
                .filter(u -> "Оценщик".equals(u.name()))
                .findFirst().orElseThrow().id();
        Long restaurantId = restaurantService.findAll().stream()
                .filter(r -> "Ресторан для отзыва".equals(r.name()))
                .findFirst().orElseThrow().id();

        ReviewRequestDTO review = new ReviewRequestDTO(userId, restaurantId, 5, "Очень вкусно!");
        ratingService.save(review);

        List<ReviewResponseDTO> reviews = ratingService.findAll();
        assertThat(reviews.stream().anyMatch(r -> "Очень вкусно!".equals(r.feedback())))
                .as("Отзыв должен быть найден по комментарию")
                .isTrue();
    }
}
