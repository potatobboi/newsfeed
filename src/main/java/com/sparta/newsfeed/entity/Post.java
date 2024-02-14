package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequsetDto requsetDto, UserDetailsImpl userDetails){
        this.title = requsetDto.getTitle();
        this.content = requsetDto.getContent();
        this.user = userDetails.getUser();
        this.username = userDetails.getUsername();
    }

    public void update(PostRequsetDto requsetDto) {
        this.title = requsetDto.getTitle();
        this.content = requsetDto.getContent();
    }
}
