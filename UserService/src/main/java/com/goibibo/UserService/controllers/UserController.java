package com.goibibo.UserService.controllers;

import com.goibibo.UserService.entities.User;
import com.goibibo.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;
    int retry = 0;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    @CircuitBreaker(name = "hotelRatingBreaker", fallbackMethod = "hotelRatingFallback")
    public ResponseEntity<List<User>> allUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    public ResponseEntity<List<User>> hotelRatingFallback(Exception ex) {
        System.out.println("HOTEL RATING FALLBACK CALLED::::::::::::: DUE TO:::::: " + ex.getMessage());
        List<User> allUsers = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(allUsers);
    }

    @Retry(name = "hotelRatingRetry", fallbackMethod = "hotelRatingFallbackRetry")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") String userId) {
        System.out.println("\n \n Retrying getUserById for " + (retry++) + " time.");
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    public ResponseEntity<User> hotelRatingFallbackRetry(@PathVariable(value = "id") String userId, Exception ex) {
        User user = new User();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user);
    }
}
