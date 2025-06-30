package com.self.esteem.controller;

import com.self.esteem.DTOs.QuestionDTO;
import com.self.esteem.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionServise;
    @PostMapping()
    QuestionDTO createQuestion(@RequestBody QuestionDTO questionDTO){
        return questionServise.createQuestion(questionDTO);
    }
    @GetMapping("/{id}")
    QuestionDTO getQuestionById(@PathVariable Long id){
        return questionServise.getQuestionById(id);
    }
    @PutMapping("/{id}")
    QuestionDTO updateQuestion(@PathVariable Long id,@RequestBody QuestionDTO questionDTO){
        return questionServise.updateQuestion(id, questionDTO);
    }
    @DeleteMapping("/{id}")
    void deleteQuestion(@PathVariable Long id){
        questionServise.deleteQuestion(id);
    }
    @GetMapping()
    List<QuestionDTO> getAllQuestions(){
        return questionServise.getAllQuestions();
    }
//    boolean canProceed(Long userId, List<AnswerDTO> answerDTOS);
//    void getPermission(Long userId, List<Long> questionId);
}
