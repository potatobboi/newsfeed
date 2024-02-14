package com.sparta.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordDto {
    @NotBlank(message = "공백일 수 없습니다.")
    String prePassword;

    @Pattern(regexp = "[a-zA-Z0-9]{8,15}", message = "비밀번호가 형식과 다릅니다.")
    String postPassword;
}
