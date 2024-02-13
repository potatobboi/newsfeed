package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.repository.PostRepository;
import org.springframework.stereotype.Service;

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
}
