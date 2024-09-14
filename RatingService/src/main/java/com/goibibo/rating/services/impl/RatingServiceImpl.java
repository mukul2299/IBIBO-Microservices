package com.goibibo.rating.services.impl;

import com.goibibo.rating.entities.Rating;
import com.goibibo.rating.repositories.RatingRepository;
import com.goibibo.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    RatingRepository repository;

    @Autowired
    RatingServiceImpl(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Rating createRating(Rating rating) {
        return repository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return repository.findAll();
    }

    @Override
    public List<Rating> getAllRatingForHotel(String hotelId) {
        return repository.findByHotelId(hotelId);
    }

    @Override
    public List<Rating> getAllRatingForUser(String userId) {
        return repository.findByUserId(userId);
    }
}
