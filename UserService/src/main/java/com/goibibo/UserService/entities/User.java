package com.goibibo.UserService.entities;

import com.goibibo.UserService.external.entities.Rating;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    private String name;
    private String email;
    private String about;
    @Id
    @Column(name = "id")
    private String userId;
    @Transient
    private List<Rating> ratings;

}
