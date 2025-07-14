package com.myrating.restaurantRatingSystem.repositories;

import com.myrating.restaurantRatingSystem.entities.Users;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<Users> users = new ArrayList<>();

    public void save(Users user){
        users.add(user);
    }

    public void remove(Users user){
        users.remove(user);
    }

    public List<Users> findAll() {
        return new ArrayList<>(users);
    }
}
