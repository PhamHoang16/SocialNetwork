package com.example.postservice.controllers;

import com.example.postservice.models.Post;
import com.example.postservice.models.ResponseObject;
import com.example.postservice.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Post")
public class PostController {

    private PostRepository repository;
    public PostController(PostRepository postRepository) {
        this.repository = postRepository;

    }

    @GetMapping("")
    List<Post> getAllPost() {
        return repository.findAll();
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createPost(@RequestBody Post newPost) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "your post has been posted", repository.save(newPost))
        );
    }


}