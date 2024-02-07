package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.dto.SignupRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<CommonResponseDto> createUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        if (userRepository.findByUsername(username).isPresent()) {
            log.error("동일한 아이디가 존재합니다.");
            return ResponseEntity.status(400).body(new CommonResponseDto("회원가입 실패", 400));
        }

        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);
        return ResponseEntity.status(200).body(new CommonResponseDto("회원가입 성공", 200));
    }
}
