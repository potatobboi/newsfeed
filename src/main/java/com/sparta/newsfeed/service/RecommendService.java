package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.RecommendRequestDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.Recommend;
import com.sparta.newsfeed.repository.PostRepository;
import com.sparta.newsfeed.repository.RecommendRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final PostRepository postRepository;

    public RecommendService(RecommendRepository recommendRepository, PostRepository postRepository) {
        this.recommendRepository = recommendRepository;
        this.postRepository = postRepository;
    }

    public Long createRecommend(RecommendRequestDto requestDto, UserDetailsImpl userDetails) { // 추천 생성
        Long postId = requestDto.getPostId();
        String username = userDetails.getUsername();
        if(!existsByPostIdAndRecommender(postId, username) && !checkWriter(postId, username)){ // 추천이 존재하지 않을경우 추천 생성 && 작성자가 아닐 경우에
            Recommend recommend = new Recommend(postId, username);
            recommendRepository.save(recommend);
        }
        return countByPostId(postId);
    }

    public Long deleteRecommend(RecommendRequestDto requestDto, UserDetailsImpl userDetails) {//추천 삭제
        Long postId = requestDto.getPostId();
       if(existsByPostIdAndRecommender(postId, userDetails.getUsername())){//추천이 존재할 경우에만 삭제
           Recommend recommend = findRecommend(postId, userDetails.getUsername());
           recommendRepository.delete(recommend);
       }
       return countByPostId(postId);
    }

    public Long getRecommend(Long postId) { //postId 기준 생성된 추천 카운트
        return countByPostId(postId);
    }



    public Recommend findRecommend(Long postId, String recommender){ //postId And recommender 기준으로 추천 가져옴
        return recommendRepository.findByPostIdAndRecommender(postId, recommender);
    }

    public boolean existsByPostIdAndRecommender(Long postId, String recommender){ //이미 추천했다면 true 아니면 false
        return recommendRepository.existsByPostIdAndRecommender(postId, recommender);
    }

    public void deleteByPostId(Long postId){ //postId 기준으로 모든 추천 삭제 Post삭제시 필요
        recommendRepository.deleteByPostId(postId);
    }

    public Long countByPostId(Long postId){//postId를 기준으로 추천을 카운트하여 반환
        return recommendRepository.countByPostId(postId);
    }

    public boolean checkWriter(Long id, String username){
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        return username.equals(post.getUsername());
    }

    public boolean recommendState(Long postId, UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return (!existsByPostIdAndRecommender(postId, username) && !checkWriter(postId, username));
    }
}
