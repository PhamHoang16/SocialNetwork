package com.example.userservice.controllers;

import com.example.userservice.dto.request.SignInForm;
import com.example.userservice.dto.request.SignUpForm;
import com.example.userservice.dto.response.JwtResponse;
import com.example.userservice.dto.response.ResponseMessage;
import com.example.userservice.model.Role;
import com.example.userservice.model.RoleName;
import com.example.userservice.model.User;
import com.example.userservice.repositories.IUserRepository;
import com.example.userservice.security.jwt.JwtProvider;
import com.example.userservice.security.userprinciple.UserPrincipal;
import com.example.userservice.service.implement.RoleServiceImpl;
import com.example.userservice.service.implement.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("")
    List<User> getUserInfo() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<User> getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("this username has been used"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("this email has been used"), HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            if (role.equals("admin")) {
                Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() ->
                        new RuntimeException("Role NOT FOUND"));
                roles.add(adminRole);
            } else {
                Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
                roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("create success!!!"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrincipal.getName(), userPrincipal.getAuthorities()));
    }
}
