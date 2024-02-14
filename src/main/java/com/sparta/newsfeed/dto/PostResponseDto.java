package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content= post.getContent();
        this.username = post.getUsername();
        this.createdDate = post.getCreatedAt();
        this.modifiedDate = post.getModifiedAt();
    }
}
