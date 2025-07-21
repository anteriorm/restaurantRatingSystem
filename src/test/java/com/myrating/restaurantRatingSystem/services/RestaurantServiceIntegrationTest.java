package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.enums.TypeKitchen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void saveAndFindRestaurant() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO("Тестовый ресторан", "Описание", TypeKitchen.RUSSIAN, 500);
        restaurantService.save(dto);

        List<RestaurantResponseDTO> restaurants = restaurantService.findAll();
        assertThat(restaurants)
                .as("Список ресторанов не должен быть пустым")
                .isNotEmpty();
        assertThat(restaurants.stream().anyMatch(r -> "Тестовый ресторан".equals(r.name())))
                .as("Ресторан должен быть найден по имени")
                .isTrue();
    }

    @Test
    void removeRestaurant() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO("Удаляемый ресторан", "Описание", TypeKitchen.EUROPEAN, 700);
        restaurantService.save(dto);
        List<RestaurantResponseDTO> restaurants = restaurantService.findAll();
        Long id = restaurants.stream()
                .filter(r -> "Удаляемый ресторан".equals(r.name()))
                .findFirst()
                .orElseThrow()
                .id();

        restaurantService.remove(id);

        List<RestaurantResponseDTO> afterRemove = restaurantService.findAll();
        assertThat(afterRemove.stream().noneMatch(r -> id.equals(r.id())))
                .as("Ресторан должен быть удалён")
                .isTrue();
    }

    @Test
    void findByMinRating() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO("Высокий рейтинг", "Описание", TypeKitchen.JAPANESE, 800);
        restaurantService.save(dto);

        List<RestaurantResponseDTO> result = restaurantService.findByMinRating(BigDecimal.valueOf(4.9));
        assertThat(result.stream().anyMatch(r -> "Высокий рейтинг".equals(r.name())))
                .as("Ресторан с высоким рейтингом должен быть найден")
                .isTrue();
    }
}

