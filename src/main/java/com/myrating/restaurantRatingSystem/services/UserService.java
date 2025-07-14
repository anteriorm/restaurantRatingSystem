package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(Users user) {
        userRepository.save(user);
    }

    public void remove(Users user) {
        userRepository.remove(user);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

}
