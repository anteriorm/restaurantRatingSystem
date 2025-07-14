package com.myrating.restaurantRatingSystem.entities;

import lombok.*;

import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private Long idUser;
    private Long idRestaurant;
    private int rating;
    private String feedback;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(idUser, rating.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUser);
    }
}
