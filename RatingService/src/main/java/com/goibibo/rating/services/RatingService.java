package com.goibibo.rating.services;


import com.goibibo.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Rating rating);
    List<Rating> getAllRating();
    List<Rating> getAllRatingForHotel(String hotelId);
    List<Rating> getAllRatingForUser(String userId);
}
