package com.goibibo.UserService.external.services;

import com.goibibo.UserService.external.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {
    @GetMapping("/ratings")
    List<Rating> findAllRatings();
    @GetMapping("/ratings/user/{userId}")
    List<Rating> findAllRatingsOfUser(@PathVariable String userId);
}
