package com.sparta.newsfeed.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디가 형식과 다릅니다.")
    private String username;

    @Pattern(regexp = "[a-zA-Z0-9]{8,15}", message = "비밀번호가 형식과 다릅니다.")
    private String password;

    private String info;
}
