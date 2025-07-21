package com.myrating.restaurantRatingSystem.services;

import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.entities.Users;
import com.myrating.restaurantRatingSystem.mappers.UserMapper;
import com.myrating.restaurantRatingSystem.repositories.UserRepository;
import com.myrating.restaurantRatingSystem.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSave() {
        UserRequestDTO dto = mock(UserRequestDTO.class);
        Users user = mock(Users.class);
        when(userMapper.toEntity(dto)).thenReturn(user);

        userService.save(dto);

        verify(userMapper).toEntity(dto);
        verify(userRepository).save(user);
    }

    @Test
    void testRemove() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);
        userService.remove(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void testFindAll() {
        Users user = mock(Users.class);
        UserResponseDTO dto = mock(UserResponseDTO.class);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        List<UserResponseDTO> result = userService.findAll();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toDto(user);
    }
}
