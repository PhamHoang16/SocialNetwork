package com.example.postservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "post")
public class Post {
    @Id
    private Long id;
    private String datetime;
    private String content;
}
