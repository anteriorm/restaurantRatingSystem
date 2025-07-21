package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void saveAndFindRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Тестовый ресторан");
        restaurant.setRating(BigDecimal.valueOf(4.5));
        Restaurant saved = restaurantRepository.save(restaurant);

        assertThat(saved.getId()).as("ID ресторана должен быть не null").isNotNull();
        assertThat(restaurantRepository.findById(saved.getId())).as("Ресторан должен быть найден по ID").isPresent();
        assertThat(restaurantRepository.findById(saved.getId()).get().getName()).as("Имя ресторана должно совпадать").isEqualTo("Тестовый ресторан");
    }
}
