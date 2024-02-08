package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.dto.MailRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j(topic = "MailService")
@Service
@RequiredArgsConstructor
public class MailService {
    private static final String senderEmail= "kudongku@gmail.com";
    private final JavaMailSender javaMailSender;

    public ResponseEntity<CommonResponseDto> createMail(MailRequestDto mailRequestDto) {
        System.out.println("mailRequestDto.toString() = " + mailRequestDto.toString());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequestDto.getReceiver());
        message.setFrom(senderEmail);
        message.setSubject(mailRequestDto.getTitle());
        message.setText(mailRequestDto.getContent());

        javaMailSender.send(message);
        return ResponseEntity.status(200).body(new CommonResponseDto("이메일 전송 성공", 200));
    }
}
