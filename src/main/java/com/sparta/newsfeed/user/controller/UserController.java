package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.dto.LoginRequestDto;
import com.sparta.newsfeed.user.dto.SignupRequestDto;
import com.sparta.newsfeed.user.jwt.JwtUtil;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> createUser(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {

            for (FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(400).body(new CommonResponseDto("회원가입 실패", 400));

        }

        return userService.createUser(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new CommonResponseDto(e.getMessage(), 400));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername()));
        return ResponseEntity.status(200).body(new CommonResponseDto("로그인 성공", 200));
    }
}
