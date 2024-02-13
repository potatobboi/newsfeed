package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Recommend findByPostIdAndRecommender(Long postId, String recommender);
    long countByPostId(Long postId);
    boolean existsByPostIdAndRecommender(Long postId, String recommender);
    void deleteByPostId(Long postId);
}
