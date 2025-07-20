package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
