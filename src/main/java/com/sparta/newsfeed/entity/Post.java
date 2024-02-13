package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Post(PostRequsetDto requsetDto){
        this.title = requsetDto.getTitle();
        this.content = requsetDto.getContent();
    }

}
