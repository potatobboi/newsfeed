package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.security.UserDetailsImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    public PostService(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
    }

    //게시물 생성
    public PostResponseDto createPost(PostRequsetDto requsetDto, UserDetailsImpl userDetails) {
        Post post = new Post(requsetDto, userDetails);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //게시물 1건 조회
    public PostResponseDto getOnePost(Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        return new PostResponseDto(post);
    }

    //게시물 전체 조회
    public List<PostResponseDto> getAllPost() {
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : postList){
            responseDtoList.add(new PostResponseDto(post));
        }

        return responseDtoList;
    }
    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postid, PostRequsetDto requsetDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));

        if(!userDetails.getUsername().equals(post.getUsername())){
            throw new RejectedExecutionException("게시물의 작성자만 수정이 가능합니다.");
        }
        post.update(requsetDto);
        return new PostResponseDto(post);
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postid, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));

        if(!userDetails.getUsername().equals(post.getUsername())){
            throw new RejectedExecutionException("게시물의 작성자만 삭제가 가능합니다.");
        }
        commentService.deleteCommentByPostId(postid);
        postRepository.delete(post);
    }

    //작성자의 전체 게시물 조회
    public List<PostResponseDto> getPostsByUserId(Long userId, UserDetailsImpl userDetails) {
        if(!userId.equals(userDetails.getUser().getId())){
            throw new RejectedExecutionException("가입된 유저가 아닙니다.");
        }
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            responseDtoList.add(new PostResponseDto(post));
        }

        return responseDtoList;
    }
}
