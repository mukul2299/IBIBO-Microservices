package com.goibibo.rating.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "user_rating")
public class Rating {
    @Id
    private String ratingId;
    private Integer rating;
    private String userId;
    private String hotelId;
    private String feedback;
}
