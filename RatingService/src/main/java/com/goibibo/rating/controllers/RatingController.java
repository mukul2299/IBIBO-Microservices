package com.goibibo.rating.controllers;

import com.goibibo.rating.entities.Rating;
import com.goibibo.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ratings")
@RestController
public class RatingController {
    RatingService ratingService;
    @Autowired
    KafkaTemplate<String, List<Rating>> kafkaTemplate;

    @Autowired
    RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRating());
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getAllRatingsByHotelId(@PathVariable String hotelId) {
        return ResponseEntity.ok(ratingService.getAllRatingForHotel(hotelId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getAllRatingsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getAllRatingForUser(userId));
    }
}
