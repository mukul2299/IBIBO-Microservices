package com.goibibo.UserService.service.impl;

import com.goibibo.UserService.entities.User;
import com.goibibo.UserService.exceptions.ResourceNotFoundException;
import com.goibibo.UserService.external.entities.Hotel;
import com.goibibo.UserService.external.entities.Rating;
import com.goibibo.UserService.external.services.HotelService;
import com.goibibo.UserService.external.services.RatingService;
import com.goibibo.UserService.repositories.UserRepository;
import com.goibibo.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    @Autowired
    UserRepository userRepository;
    @Autowired
    HotelService hotelService;
    @Autowired
    RatingService ratingService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<User> getAllUsers() {
        CompletableFuture<List<User>> usersFuture = CompletableFuture.supplyAsync(() -> userRepository.findAllUsers(), executorService);
        CompletableFuture<List<Rating>> ratingsFuture = CompletableFuture.supplyAsync(() -> fetchAllRatings(null), executorService);
        return usersFuture.thenCombine(ratingsFuture, (users, ratings) -> {
            String hotelIds = ratings.stream().map(Rating::getHotelId).distinct().collect(Collectors.joining(","));
            CompletableFuture<Map<String, Hotel>> hotelsFuture = CompletableFuture.supplyAsync(() -> fetchAllHotel(hotelIds), executorService);
            Map<String, List<Rating>> ratingsByUserId = ratings.stream().collect(Collectors.groupingBy(Rating::getUserId));
            Map<String, Hotel> hotelMapping = hotelsFuture.join();
            for (User u : users) {
                if (!CollectionUtils.isEmpty(ratingsByUserId.get(u.getUserId()))) {
                    ratingsByUserId.get(u.getUserId()).forEach(r -> r.setHotel(hotelMapping.get(r.getHotelId())));
                    u.setRatings(ratingsByUserId.get(u.getUserId()));
                }
            }
            return users;
        }).join();
    }

    @Override
    public User getUserById(String userId) {
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUserById(userId), executorService);
        CompletableFuture<List<Rating>> ratingsFuture = CompletableFuture.supplyAsync(() -> fetchAllRatings(userId), executorService);
        return userFuture.thenCombine(ratingsFuture, (user, ratings) -> {
            if (user == null) {
                throw new ResourceNotFoundException("User not found with given id: " + userId);
            } else {
                String hotelIds = ratings.stream().map(Rating::getHotelId).distinct().collect(Collectors.joining(","));
                CompletableFuture<Map<String, Hotel>> hotelsFuture = CompletableFuture.supplyAsync(() -> fetchAllHotel(hotelIds), executorService);
                Map<String, Hotel> hotelMapping = hotelsFuture.join();
                ratings.forEach(r -> r.setHotel(hotelMapping.get(r.getHotelId())));
                user.setRatings(ratings);
                return user;
            }
        }).join();

    }


    @Override
    public User saveNewUser(User newUser) {
        newUser.setUserId(UUID.randomUUID().toString());
        return userRepository.save(newUser);
    }

    private List<Rating> fetchAllRatings(String userId) {
        if (userId == null) {
            return ratingService.findAllRatings();
        } else {
            return ratingService.findAllRatingsOfUser(userId);
        }
//        String ratingServiceUrl1 = userId == null ? "http://RATINGSERVICE/ratings" : "http://RATINGSERVICE/ratings/user/" + userId;
//        return restTemplate.exchange(ratingServiceUrl1, HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {}).getBody();
    }

    private Map<String, Hotel> fetchAllHotel(String hotelIds) {
//        Hotel[] hotelArray = restTemplate.getForObject("http://HOTELSERVICE/hotels?" + hotelIds, Hotel[].class);
        List<Hotel> hotelArray = hotelService.findHotels(hotelIds);
        if (hotelArray != null) {
            return hotelArray.stream().collect(Collectors.toMap(Hotel::getId, hotel -> hotel));
        }
        return null;
    }
}
