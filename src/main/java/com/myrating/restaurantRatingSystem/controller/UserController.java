package com.myrating.restaurantRatingSystem.controller;

import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping
    public void addUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        userService.save(userRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id){
        userService.remove(id);
    }
}
