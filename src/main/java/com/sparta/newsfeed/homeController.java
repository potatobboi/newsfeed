package com.sparta.newsfeed;

import com.sparta.newsfeed.user.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class homeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        return "index";
    }

    @GetMapping("/api/posts")
    public String test1(){
        return "index";
    }

    @GetMapping("/api/posts/{id}")
    public String test2(){
        return "index";
    }
}
