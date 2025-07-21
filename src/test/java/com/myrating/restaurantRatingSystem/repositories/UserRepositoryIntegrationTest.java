package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.AbstractIntegrationTest;
import com.myrating.restaurantRatingSystem.entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindUser() {
        Users user = new Users();
        user.setName("Тестовый пользователь");
        user.setAge(5);
        user.setGender("мужской");
        Users saved = userRepository.save(user);

        assertThat(saved.getId()).as("ID пользователя должен быть не null").isNotNull();
        assertThat(userRepository.findById(saved.getId())).as("Пользователь должен быть найден по ID").isPresent();
        assertThat(userRepository.findById(saved.getId()).get().getName()).as("Имя пользователя должно совпадать").isEqualTo("Тестовый пользователь");
    }
}
