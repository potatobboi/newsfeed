package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.*;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 프로필 수정
    @Transactional
    public UserInfoDto updateUserInfo(UpdateUserDto updateUserDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

        user.update(updateUserDto.getInfo());
        return new UserInfoDto(username, user.getInfo());
    }

    @Transactional // 비밀번호 수정
    public CommonResponseDto updatePassword(UpdatePasswordDto updatePasswordDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

        if (!passwordEncoder.matches(updatePasswordDto.getPrePassword(), user.getPassword())) {
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(updatePasswordDto.getPostPassword());
        user.updatePassword(encodedPassword);

        return new CommonResponseDto("비밀번호 수정 성공", 200);
    }
}
