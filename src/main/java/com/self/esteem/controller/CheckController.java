package com.self.esteem.controller;

import com.self.esteem.DTOs.AnswerDTO;
import com.self.esteem.feign.JwtTokenUtil;
import com.self.esteem.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/check")
public class CheckController {
    private final QuestionService questionService;
    private final JwtTokenUtil jwtTokenUtil;

    public CheckController(QuestionService questionService, JwtTokenUtil jwtTokenUtil) {
        this.questionService = questionService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping()
    public ResponseEntity<String> check(@RequestHeader("Authorization") String token,
                                        @RequestBody List<AnswerDTO> answers) {
        Long userId = jwtTokenUtil.extractUserEmail(token);
        boolean result = questionService.canProceed(userId, answers);
        return result
                ? ResponseEntity.ok("Cavablar qəbul edildi.")
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zəhmət olmasa filiala yaxınlaşın.");
    }
}
