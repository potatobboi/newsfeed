package com.sparta.newsfeed.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto {
    private String username;
    private String password;
}
