package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.MailRequestDto;
import com.sparta.newsfeed.user.entity.Mail;
import com.sparta.newsfeed.user.repository.MailRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private static final String senderEmail = "kudongku@gmail.com";
    private final PasswordEncoder passwordEncoder;
    private final MailRepository mailRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public void createMail(MailRequestDto mailRequestDto) {
        if (userRepository.findByEmail(mailRequestDto.getReceiverEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        } else if (mailRepository.findByEmail(mailRequestDto.getReceiverEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 인증메일을 보냈습니다.");
        }

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
    }
}
