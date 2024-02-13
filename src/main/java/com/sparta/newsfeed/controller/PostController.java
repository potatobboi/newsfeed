package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.service.PostService;
import org.springframework.web.bind.annotation.*;

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

    //게시물 1건 조회
    @GetMapping("/{postid}")
    public PostResponseDto getOnePost(@PathVariable Long postid){
        return postService.getOnePost(postid);
    }

    //게시물 수정
    @PutMapping("/{postid}")
    public PostResponseDto updatePost(@PathVariable Long postid, @RequestBody PostRequsetDto requsetDto){
        return postService.updatePost(postid, requsetDto);
    }

    //게시물 삭제
    @DeleteMapping("/{postid}")
    public void deletePost(@PathVariable Long postid){
        postService.deletePost(postid);
    }
}
