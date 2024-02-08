package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentResponseDto;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.repository.CommentRepository;
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

    public CommentResponseDto createComment(CommentRequestDto requestDto) { // 댓글 생성
        Comment comment = new Comment(requestDto);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public Long updateComment(Long id, CommentRequestDto requestDto) { //댓글 id를 기준으로 댓글 update
        Comment comment = findyComment(id);
        comment.update(requestDto);
        return id;
    }

    public Long deleteComment(Long id) { //댓글 id를 기준으로 댓글삭제
        Comment comment = findyComment(id);
        commentRepository.delete(comment);
        return id;
    }

    public Comment findyComment(Long id){// id를 기준으로 댓글 찾아옴
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }

    public boolean deleteCommentByPostId(Long postId){ //post의 id를 기준으로 모든 댓글 삭제
        commentRepository.deleteByPostId(postId);
        return false;
    }


}
