package com.myrating.restaurantRatingSystem;

import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.myrating.restaurantRatingSystem.repositories")
public class RestaurantRatingSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RestaurantRatingSystemApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		System.out.println();
		Users user2 = new Users(2L,"Паша",20,"Мужской");
		System.out.println("Пользователи:");
	}

}
