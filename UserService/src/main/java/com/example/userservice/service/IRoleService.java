package com.example.userservice.service;

import com.example.userservice.model.Role;
import com.example.userservice.model.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
