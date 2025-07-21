package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void saveAndFindUser() {
        UserRequestDTO dto = new UserRequestDTO("Пользователь сервис", 8, "мужской");
        userService.save(dto);

        List<UserResponseDTO> users = userService.findAll();
        assertThat(users)
                .as("Список пользователей не должен быть пустым")
                .isNotEmpty();
        assertThat(users.stream().anyMatch(u -> "Пользователь сервис".equals(u.name())))
                .as("Пользователь должен быть найден по имени")
                .isTrue();
    }

    @Test
    void removeUser() {
        UserRequestDTO dto = new UserRequestDTO("Удаляемый пользователь", 7, "женский");
        userService.save(dto);
        List<UserResponseDTO> users = userService.findAll();
        Long id = users.stream()
                .filter(u -> "Удаляемый пользователь".equals(u.name()))
                .findFirst()
                .orElseThrow()
                .id();

        userService.remove(id);

        List<UserResponseDTO> afterRemove = userService.findAll();
        assertThat(afterRemove.stream().noneMatch(u -> id.equals(u.id())))
                .as("Пользователь должен быть удалён")
                .isTrue();
    }
}
