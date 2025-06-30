package com.self.esteem.services;

import com.self.esteem.DTOs.AnswerDTO;
import com.self.esteem.DTOs.QuestionDTO;

import java.util.List;

public interface QuestionService {
    QuestionDTO createQuestion(QuestionDTO questionDTO);
    QuestionDTO getQuestionById(Long id);
    QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO);
    void deleteQuestion(Long id);
    List<QuestionDTO> getAllQuestions();
    boolean canProceed(Long userId, List<AnswerDTO> answerDTOS);
    void addPermission(Long userId, List<Long> questionId);
}
