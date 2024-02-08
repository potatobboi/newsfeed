package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.dto.MailRequestDto;
import com.sparta.newsfeed.user.entity.Mail;
import com.sparta.newsfeed.user.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "MailService")
@Service
@RequiredArgsConstructor
public class MailService {
    private static final String senderEmail = "kudongku@gmail.com";
    private final PasswordEncoder passwordEncoder;
    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;

    public ResponseEntity<CommonResponseDto> createMail(MailRequestDto mailRequestDto) {
        String title = "[오운식뭐] 가입 인증 메일입니다.";
        String contentHeader = "인증번호는\n";
        String contentFooter = "\n입니다.";
        String encodedEmail = passwordEncoder.encode(mailRequestDto.getReceiverEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequestDto.getReceiverEmail());
        message.setFrom(senderEmail);
        message.setSubject(title);
        message.setText(contentHeader + encodedEmail + contentFooter);

        javaMailSender.send(message);
        mailRepository.save(new Mail(mailRequestDto.getReceiverEmail(), encodedEmail));
        return ResponseEntity.status(200).body(new CommonResponseDto("이메일 전송 성공", 200));
    }
}
