package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.mappers.UserMapper;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void save(UserRequestDTO dto) {
        Users user = userMapper.toEntity(dto);
        userRepository.save(user);
    }

    public void remove(Long id){
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

}
