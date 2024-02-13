package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.*;
import com.sparta.newsfeed.user.entity.Mail;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.MailRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String email = signupRequestDto.getEmail();
        String encodedEmail = signupRequestDto.getEncodedEmail();
        Mail mail = mailRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 이메일")
        );

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("동일한 아이디가 존재합니다.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("동일한 이메일이 존재합니다.");
        }
        if (!(mail.getEncodedEmail().equals(encodedEmail))) {
            throw new IllegalArgumentException("이메일 인증 실패.");
        }

        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);
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
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(updatePasswordDto.getPostPassword());
        user.updatePassword(encodedPassword);

        return new CommonResponseDto("비밀번호 수정 성공", 200);
    }
}
