package com.myrating.restaurantRatingSystem.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RatingId implements Serializable {
    private Long userId;
    private Long restaurantId;

}