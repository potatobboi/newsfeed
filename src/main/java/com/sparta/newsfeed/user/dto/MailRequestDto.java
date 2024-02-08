package com.sparta.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailRequestDto {
    private String receiver;
    private String title;
    private String content;
}