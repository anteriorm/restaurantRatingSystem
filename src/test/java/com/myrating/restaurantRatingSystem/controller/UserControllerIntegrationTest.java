package com.myrating.restaurantRatingSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrating.restaurantRatingSystem.dto.UserRequestDTO;
import com.myrating.restaurantRatingSystem.dto.UserResponseDTO;
import com.myrating.restaurantRatingSystem.exception.ResourceNotFoundException;
import com.myrating.restaurantRatingSystem.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ImportAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        List<UserResponseDTO> users = List.of(
                new UserResponseDTO(1L, "Ivan", 5, "male"),
                new UserResponseDTO(2L, "Anna", 7, "female")
        );
        Mockito.when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Anna"));
    }

    @Test
    void removeUser_notFound_shouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("User not found")).when(userService).remove(999L);
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void addUser_shouldCallServiceAndReturnOk() throws Exception {
        UserRequestDTO dto = new UserRequestDTO("Ivan", 5, "male");
        doNothing().when(userService).save(any(UserRequestDTO.class));
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void removeUser_shouldCallServiceAndReturnOk() throws Exception {
        doNothing().when(userService).remove(1L);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}