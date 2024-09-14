package com.goibibo.UserService.external.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String ratingId;
    private Integer rating;
    private String userId;
    private String hotelId;
    private String feedback;
    private Hotel hotel;
}
