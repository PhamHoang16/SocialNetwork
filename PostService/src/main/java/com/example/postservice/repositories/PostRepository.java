package com.example.postservice.repositories;

import com.example.postservice.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends MongoRepository<Post, Long> {
}
