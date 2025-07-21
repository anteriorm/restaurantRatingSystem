package com.myrating.restaurantRatingSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrating.restaurantRatingSystem.dto.RestaurantRequestDTO;
import com.myrating.restaurantRatingSystem.dto.RestaurantResponseDTO;
import com.myrating.restaurantRatingSystem.enums.TypeKitchen;
import com.myrating.restaurantRatingSystem.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantService restaurantService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRestaurants_shouldReturnList() throws Exception {
        List<RestaurantResponseDTO> restaurants = List.of(
                new RestaurantResponseDTO(1L, "Test", "desc", TypeKitchen.EUROPEAN, 1000, BigDecimal.valueOf(4.5)),
                new RestaurantResponseDTO(2L, "Test2", "desc2", TypeKitchen.ITALIAN, 2000, BigDecimal.valueOf(4.0))
        );
        Mockito.when(restaurantService.findAll()).thenReturn(restaurants);
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Test2"));
    }

    @Test
    void getByMinRating_shouldReturnFiltered() throws Exception {
        List<RestaurantResponseDTO> restaurants = List.of(
                new RestaurantResponseDTO(1L, "Test", "desc", TypeKitchen.EUROPEAN, 1000, BigDecimal.valueOf(4.5))
        );
        Mockito.when(restaurantService.findByMinRating(BigDecimal.valueOf(4.0))).thenReturn(restaurants);
        mockMvc.perform(get("/api/restaurants/min-rating?minRating=4.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(4.5));
    }

    @Test
    void addRestaurant_shouldCallServiceAndReturnOk() throws Exception {
        RestaurantRequestDTO dto = new RestaurantRequestDTO("Test", "desc", TypeKitchen.EUROPEAN, 1000);
        doNothing().when(restaurantService).save(any(RestaurantRequestDTO.class));
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void removeRestaurant_shouldCallServiceAndReturnOk() throws Exception {
        doNothing().when(restaurantService).remove(1L);
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());
    }
}
