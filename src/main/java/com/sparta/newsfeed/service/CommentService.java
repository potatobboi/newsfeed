package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentResponseDto;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.repository.CommentRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public List<CommentResponseDto> getComments(Long postId) { // 게시글 id를 기준으로 속해있는 모든 댓글을 가져옴
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) { // 댓글 생성
        Comment comment = new Comment(requestDto, userDetails);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public Long updateComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) { //댓글 id를 기준으로 댓글 update
        Comment comment = findyComment(id);
        if(validateUsername(comment, userDetails)){ // 작성자와 로그인한 user가 일치할 경우에만 업데이트
            comment.update(requestDto, userDetails);
            return id;
        }else return -id;
    }

    public Long deleteComment(Long id, UserDetailsImpl userDetails) { //댓글 id를 기준으로 댓글삭제
        Comment comment = findyComment(id);
        if(validateUsername(comment,userDetails)){
            commentRepository.delete(comment);
            return id;
        }else return -id;
    }

    public Comment findyComment(Long id){// id를 기준으로 댓글 찾아옴
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }

    public void deleteCommentByPostId(Long postId){ //post의 id를 기준으로 모든 댓글 삭제
        commentRepository.deleteByPostId(postId);
    }

    public boolean validateUsername(Comment comment, UserDetailsImpl userDetails){
        if (comment.getUsername().equals(userDetails.getUsername()))return true;
        return false;
    }


}
