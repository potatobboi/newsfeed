package com.sparta.newsfeed.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long postId;

    private String commentContent;

    @Override
    public String toString() {
        return "CommentRequestDto{" +
                "postId=" + postId +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}
