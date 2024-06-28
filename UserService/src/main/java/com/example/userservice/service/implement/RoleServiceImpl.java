package com.example.userservice.service.implement;

import com.example.userservice.model.Role;
import com.example.userservice.model.RoleName;
import com.example.userservice.repositories.IRoleRepository;
import com.example.userservice.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
