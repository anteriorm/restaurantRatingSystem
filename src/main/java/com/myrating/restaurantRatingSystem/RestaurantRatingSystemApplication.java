package com.myrating.restaurantRatingSystem;

import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class RestaurantRatingSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RestaurantRatingSystemApplication.class, args);
		UserService userService = context.getBean(UserService.class);
	}

}
