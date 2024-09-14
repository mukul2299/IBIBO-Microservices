package com.goibibo.UserService.service;

import com.goibibo.UserService.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User getUserById(String userId);
    User saveNewUser(User newUser);
}
