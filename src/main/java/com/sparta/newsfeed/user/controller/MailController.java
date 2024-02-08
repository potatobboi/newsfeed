package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.MailRequestDto;
import com.sparta.newsfeed.user.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "MailController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MailController {
    private final MailService mailService;

    @PostMapping("/mails")
    public void MailSend(@RequestBody MailRequestDto mailRequestDto){
        mailService.createMail(mailRequestDto);
    }
}
