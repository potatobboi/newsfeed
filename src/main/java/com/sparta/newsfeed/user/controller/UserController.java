package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.SignupRequestDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "UserController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userservice;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if(!fieldErrors.isEmpty()){

            for(FieldError fieldError : fieldErrors){
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>("회원가입 실패", HttpStatusCode.valueOf(401));

        }

        return userservice.createUser(signupRequestDto);
    }
}
