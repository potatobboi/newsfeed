package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시물 생성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequsetDto requsetDto){
        return postService.createPost(requsetDto);
    }

}
