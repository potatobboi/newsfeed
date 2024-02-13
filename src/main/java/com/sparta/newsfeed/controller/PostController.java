package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.service.PostService;
import com.sparta.newsfeed.user.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시물 생성
    @PostMapping("/create")
    public String createPost(@RequestBody PostRequsetDto requsetDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.createPost(requsetDto, userDetails);
        return "redirect:/api/posts";
    }

    //작성자의 전체 게시물 조회
    @ResponseBody
    @GetMapping("/userId/{userId}")
    public List<PostResponseDto> getPostsByUserId(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPostsByUserId(userId, userDetails);
    }

    //게시물 1건 조회
    @ResponseBody
    @GetMapping("/postId/{postId}")
    public PostResponseDto getOnePost(@PathVariable Long postId){
        return postService.getOnePost(postId);
    }

    //게시물 전체 조회
    @ResponseBody
    @GetMapping
    public List<PostResponseDto> getAllPost(){
        return postService.getAllPost();
    }

    //게시물 수정
    @PutMapping("/{postid}")
    public String updatePost(@PathVariable Long postid, @RequestBody PostRequsetDto requsetDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.updatePost(postid, requsetDto, userDetails);
        return "redirect:/api/posts";
    }

    //게시물 삭제
    @DeleteMapping("/{postid}")
    public String deletePost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(postid, userDetails);
        return "redirect:/api/posts";
    }
}
