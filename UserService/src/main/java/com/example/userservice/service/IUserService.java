package com.example.userservice.service;

import com.example.userservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserService {
    Optional<User> findByUsername(String name);
    Boolean existsByUsername(String username);               //username da co trong db chuaw khi tao du lieu
    Boolean existsByEmail(String email);
    User save(User user);
}
