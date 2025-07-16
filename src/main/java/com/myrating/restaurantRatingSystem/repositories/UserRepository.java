package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Users;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<Users> users = new ArrayList<>();
    private static long COUNTER = 1;

    public void save(Users user){
        if (user.getId() == null) {
            user.setId(COUNTER++);
        }
        users.add(user);
    }

    public void remove(Users user){
        users.remove(user);
    }

    public List<Users> findAll() {
        return new ArrayList<>(users);
    }

    public Users findById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
