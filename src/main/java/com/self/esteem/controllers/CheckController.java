package com.self.esteem.controllers;

import com.self.esteem.DTOs.AnswerDTO;
import com.self.esteem.feign.JwtTokenUtil;
import com.self.esteem.generators.OtpGenerator;
import com.self.esteem.services.QuestionService;
import com.self.esteem.services.impl.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/check")
public class CheckController {
    private final QuestionService questionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final OtpGenerator otpGenerator;
    private final EmailService emailService;

    public CheckController(QuestionService questionService, JwtTokenUtil jwtTokenUtil, OtpGenerator otpGenerator, EmailService emailService) {
        this.questionService = questionService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
    }

    @PostMapping()
    public ResponseEntity<String> check(@RequestHeader("Authorization") String token,
                                        @RequestBody List<AnswerDTO> answers) {
        Long userId = jwtTokenUtil.extractUserId(token);
        boolean result = questionService.canProceed(userId, answers);

        if(result){
            String email = jwtTokenUtil.extractUserEmail(token);
            String otp = otpGenerator.generateOtp();
            emailService.sendOTP(email,otp);
            return ResponseEntity.ok("Cavablar qəbul edildi.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zəhmət olmasa filiala yaxınlaşın.");
    }
}
