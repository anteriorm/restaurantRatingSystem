package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.RatingId;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void saveAndFindReview() {
        Users user = new Users();
        user.setName("Пользователь для отзыва");
        user.setAge(7);
        user.setGender("женский");
        user = userRepository.save(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Ресторан для отзыва");
        restaurant.setRating(BigDecimal.valueOf(3.7));
        restaurant = restaurantRepository.save(restaurant);

        Rating rating = new Rating();
        rating.setId(new RatingId(user.getId(), restaurant.getId()));
        rating.setUser(user);
        rating.setRestaurant(restaurant);
        rating.setRating(4);
        rating = ratingRepository.save(rating);

        assertThat(ratingRepository.findById(new RatingId(user.getId(), restaurant.getId())))
                .as("Отзыв должен быть найден по составному ключу").isPresent();
        assertThat(ratingRepository.findById(new RatingId(user.getId(), restaurant.getId())).get().getRating())
                .as("Оценка должна совпадать").isEqualTo(4);
    }
}
