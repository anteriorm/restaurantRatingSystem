package com.myrating.restaurantRatingSystem.entities;

import lombok.*;


import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private Long id;
    private String name;
    private int age;
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
