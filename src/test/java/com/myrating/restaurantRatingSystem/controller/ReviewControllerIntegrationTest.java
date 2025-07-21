package com.myrating.restaurantRatingSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrating.restaurantRatingSystem.dto.ReviewRequestDTO;
import com.myrating.restaurantRatingSystem.dto.ReviewResponseDTO;
import com.myrating.restaurantRatingSystem.exception.BadRequestException;
import com.myrating.restaurantRatingSystem.services.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RatingService ratingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRatings_shouldReturnList() throws Exception {
        List<ReviewResponseDTO> reviews = List.of(
                new ReviewResponseDTO(1L, 2L, 5, "Good"),
                new ReviewResponseDTO(2L, 3L, 4, "Nice")
        );
        Mockito.when(ratingService.findAll()).thenReturn(reviews);
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUser").value(1L))
                .andExpect(jsonPath("$[1].feedback").value("Nice"));
    }

    @Test
    void addReview_twice_shouldReturn400() throws Exception {
        doThrow(new BadRequestException("Пользователь уже оставил отзыв")).when(ratingService)
                .save(any(ReviewRequestDTO.class));
        ReviewRequestDTO dto = new ReviewRequestDTO(1L, 2L, 5, "Крутое место!");
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Пользователь уже оставил отзыв"));
    }

    @Test
    void getByIdRatings_shouldReturnReview() throws Exception {
        ReviewResponseDTO review = new ReviewResponseDTO(1L, 2L, 5, "Good");
        Mockito.when(ratingService.findById(1L, 2L)).thenReturn(review);
        mockMvc.perform(get("/api/reviews/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(1L))
                .andExpect(jsonPath("$.idRestaurant").value(2L));
    }

    @Test
    void addRating_shouldCallServiceAndReturnOk() throws Exception {
        ReviewRequestDTO dto = new ReviewRequestDTO(1L, 2L, 5, "Good");
        doNothing().when(ratingService).save(any(ReviewRequestDTO.class));
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void removeRating_shouldCallServiceAndReturnOk() throws Exception {
        doNothing().when(ratingService).remove(1L, 2L);
        mockMvc.perform(delete("/api/reviews/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void getPaged_shouldReturnPage() throws Exception {
        List<ReviewResponseDTO> reviews = List.of(
                new ReviewResponseDTO(1L, 2L, 5, "Good")
        );
        Page<ReviewResponseDTO> page = new PageImpl<>(reviews, PageRequest.of(0, 1), 1);
        Mockito.when(ratingService.findAllPaged(0, 1, "rating", false)).thenReturn(page);
        mockMvc.perform(get("/api/reviews/paged?page=0&size=1&sortBy=rating&asc=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idUser").value(1L));
    }
}
