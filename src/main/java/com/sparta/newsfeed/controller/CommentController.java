package com.sparta.newsfeed.controller;


import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentResponseDto;
import com.sparta.newsfeed.service.CommentService;
import com.sparta.newsfeed.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponseDto> getComments(@RequestParam Long postId){//postId기준 모든 댓글 조회
        return commentService.getComments(postId);
    }

    @PostMapping("/create")//댓글 생성
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("requestDto = " + requestDto.toString());
        return commentService.createComment(requestDto, userDetails);
    }

    @PutMapping("/{id}")//댓글 수정
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id,requestDto, userDetails);
    }

    @DeleteMapping("/{id}")//댓글 삭제
    public Long deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails);
    }

}
