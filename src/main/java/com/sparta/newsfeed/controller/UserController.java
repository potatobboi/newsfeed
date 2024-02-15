package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.*;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "UserController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> createUser(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (FieldError fieldError : fieldErrors) {
                sb.append("\n").append(fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(400).body(new CommonResponseDto(sb.toString(), 400));
        }

        try {
            userService.createUser(signupRequestDto);
            return ResponseEntity.status(200).body(new CommonResponseDto("회원가입 성공", 200));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new CommonResponseDto(e.getMessage(), 200));
        }
    }

    @GetMapping // 프로필 조회
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        String info = userDetails.getUser().getInfo();

        return ResponseEntity.ok(new UserInfoDto(username, info));
    }

    @PutMapping // 프로필 수정
    public ResponseEntity<UserInfoDto> updateUserInfo(@RequestBody UpdateUserDto updateUserDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoDto userInfoDto = userService.updateUserInfo(updateUserDto, userDetails.getUser().getUsername());

        return ResponseEntity.ok(userInfoDto);
    }

    @PatchMapping("/password") // 비밀번호 수정
    public ResponseEntity<CommonResponseDto> updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            userService.updatePassword(updatePasswordDto, userDetails.getUser().getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto("비밀번호 수정 성공", HttpStatus.OK.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class) // IllegalArgumentException 핸들러
    public ResponseEntity<ExceptionDto> IllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .state(HttpStatus.BAD_REQUEST)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 비밀번호 예외처리 핸들러
    public ResponseEntity<ExceptionDto> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .state(HttpStatus.BAD_REQUEST)
                        .message(e.getMessage())
                        .build());
    }
}