package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.RecommendRequestDto;
import com.sparta.newsfeed.service.RecommendService;
import com.sparta.newsfeed.user.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommends")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping  // 추천 가져오기
    public Long getRecommend(@RequestParam Long postId){
        return recommendService.getRecommend(postId);
    }

    @PostMapping("/create") // 추천 생성하기
    public Long createRecommend(@RequestBody RecommendRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUsername());
        return recommendService.createRecommend(requestDto, userDetails);
    }

    @DeleteMapping("/delete") // 추천 삭제하기
    public Long deleteRecommend(@RequestBody RecommendRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recommendService.deleteRecommend(requestDto, userDetails);
    }


}
