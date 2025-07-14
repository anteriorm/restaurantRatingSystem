package com.myrating.restaurantRatingSystem;

import com.myrating.restaurantRatingSystem.entities.Rating;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class RestaurantRatingSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RestaurantRatingSystemApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		System.out.println();
		Users user2 = new Users(2L,"Паша",20,"Мужской");
		userService.remove(user2);
		List<Users> users = userService.findAll();
		System.out.println("Пользователи:");
		for (Users user : users) {
			System.out.println(user);
		}
	}

}
