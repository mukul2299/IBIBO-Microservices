package com.goibibo.rating.repositories;

import com.goibibo.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepository extends MongoRepository<Rating,String> {
    List<Rating> findByHotelId(String hotelId);
    List<Rating> findByUserId(String userId);
}
