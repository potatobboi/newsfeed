package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String info;

    @Column(nullable = false, unique = true)
    private String email;

    public User(SignupRequestDto signupRequestDto, String encodedPassword) {
        this.username = signupRequestDto.getUsername();
        this.password = encodedPassword;
        this.email = signupRequestDto.getEmail();

        if(info == null){
            info = signupRequestDto.getInfo();
        }
    }

    // 프로필 수정
    public void update(String info) {
        this.info = info;
    }

    // 비밀번호 수정
    public void updatePassword(String postPassword) {
        this.password = postPassword;
    }
}
