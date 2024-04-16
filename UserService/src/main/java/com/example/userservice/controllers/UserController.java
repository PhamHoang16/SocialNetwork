package com.example.userservice.controllers;

import com.example.userservice.models.ResponseObject;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("")
    List<User> getAllUser() {
        return repository.findAll();
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createUser(@RequestBody User newUser) {
        List<User> foundUser = repository.findByUsername(newUser.getUsername().trim());
        if (!foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "this user name has been used", repository.save(newUser))
            );
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "register successfully", repository.save(newUser))
        );
    }
}
