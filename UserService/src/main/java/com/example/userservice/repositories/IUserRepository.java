package com.example.userservice.repositories;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    Boolean existsByUsername(String username);               //username da co trong db chuaw khi tao du lieu
    Boolean existsByEmail(String email);
    Optional<User> findById(Long id);
}
