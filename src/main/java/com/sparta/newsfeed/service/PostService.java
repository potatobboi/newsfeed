package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시물 생성
    public PostResponseDto createPost(PostRequsetDto requsetDto) {
        Post post = new Post(requsetDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    public PostResponseDto getOnePost(Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        return new PostResponseDto(post);
    }

    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postid, PostRequsetDto requsetDto) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        post.update(requsetDto);
        return new PostResponseDto(post);
    }

    //게시물 삭제
    public void deletePost(Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        postRepository.delete(post);
    }
}
