package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Restaurant;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public void save(UserRequestDTO dto) {
        Users user = new Users();
        user.setName(dto.name());
        user.setAge(dto.age());
        user.setGender(dto.gender());
        userRepository.save(user);
    }

    public void remove(Long id){
        Users users = userRepository.findById(id);
        if (users != null) {
            userRepository.remove(users);
        }
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getAge(), user.getGender()))
                .toList();
    }

}
