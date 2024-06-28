package com.example.postservice;

import com.example.postservice.models.ConfigApp;
import com.example.postservice.models.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class PostServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PostServiceApplication.class, args);

	}

}
