package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.SignupRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        if(userRepository.findByUsername(username).isPresent()){
            log.error("동일한 아이디가 존재합니다.");
            return new ResponseEntity<>("동일한 아이디가 존재합니다.", HttpStatusCode.valueOf(402));
        }

        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);
        return new ResponseEntity<>("회원가입 성공", HttpStatusCode.valueOf(200));
    }
}
