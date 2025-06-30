package com.self.esteem.services.impl;

import com.self.esteem.DTOs.AnswerDTO;
import com.self.esteem.DTOs.QuestionDTO;
import com.self.esteem.entities.Questions;
import com.self.esteem.entities.QuestionsPermission;
import com.self.esteem.repositories.QuestionPermissionsRepository;
import com.self.esteem.repositories.QuestionRepository;
import com.self.esteem.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionPermissionsRepository questionPermissionsRepository;
    private final ModelMapper modelMapper;
    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {

        Questions question = new Questions();
        modelMapper.map(questionDTO, question);

        Questions savedQuestion = questionRepository.save(question);

        return modelMapper.map(savedQuestion, QuestionDTO.class);

    }

    @Override
    public QuestionDTO getQuestionById(Long id) {

        Questions question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Questions question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        modelMapper.map(questionDTO, question);

        Questions updatedQuestion = questionRepository.save(question);

        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }

    @Override
    public void deleteQuestion(Long id) {
        Questions question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        questionRepository.delete(question);
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Questions> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .toList();
    }

    @Override
    public boolean canProceed(Long userId, List<AnswerDTO> answerDTOS) {
        List<Long> answeredQuestionIds = answerDTOS.stream()
                .filter(AnswerDTO::isAnswer)
                .map(AnswerDTO::getQuestionId)
                .toList();

        if(answeredQuestionIds.isEmpty()) {
            return true;
        }

        List<Long> allowedQuestionIds = questionPermissionsRepository
                                    .findAllByUserId(userId)
                                    .stream()
                                    .map(permission -> permission.getQuestion().getId())
                                    .toList();

        for (Long questionId : answeredQuestionIds) {
            if (!allowedQuestionIds.contains(questionId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void addPermission(Long userId, List<Long> questionId) {
        for (Long id : questionId) {
            if (!questionPermissionsRepository.existsByQuestionIdAndUserId(userId, id)) {
                QuestionsPermission question = QuestionsPermission.builder().userId(userId)
                        .question(questionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Question not found with id: " + id)))
                        .build();
                questionPermissionsRepository.save(question);
            }
        }
    }
}
