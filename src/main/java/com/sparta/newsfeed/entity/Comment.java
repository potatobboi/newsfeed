package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String commentContent;

    public Comment (CommentRequestDto requestDto){
        this.postId = requestDto.getPostId();
        this.username = requestDto.getUsername();
        this.commentContent = requestDto.getCommentContent();
    }


    public void update(CommentRequestDto requestDto) {
        this.postId = requestDto.getPostId();
        this.username = requestDto.getUsername();
        this.commentContent = requestDto.getCommentContent();
    }
}