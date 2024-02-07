package com.sparta.newsfeed.user.security;

import com.sparta.newsfeed.user.dto.LoginRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetailsByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new NullPointerException("존재하지 않는 유저네임입니다.")
        );

        return new UserDetailsImpl(user);
    }
}
