package com.goibibo.UserService.repositories;

import com.goibibo.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("select u from User u")
    List<User> findAllUsers();
    @Query("select u from User u where u.userId= :userId")
    User findUserById( String userId);
}
