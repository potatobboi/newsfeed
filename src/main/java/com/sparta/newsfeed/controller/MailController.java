package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.CommonResponseDto;
import com.sparta.newsfeed.dto.MailRequestDto;
import com.sparta.newsfeed.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MailController {
    private final MailService mailService;

    @PostMapping("/mails")
    public ResponseEntity<CommonResponseDto> MailSend(@RequestBody MailRequestDto mailRequestDto) {
        try{
            mailService.createMail(mailRequestDto);
            return ResponseEntity.status(200).body(new CommonResponseDto("이메일 전송 성공", 200));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(new CommonResponseDto(e.getMessage(), 400));
        }
    }
}
