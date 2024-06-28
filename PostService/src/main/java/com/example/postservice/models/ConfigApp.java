package com.example.postservice.models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigApp {

    @Bean
    public Post createPost() {
        return new Post();
    }
}

