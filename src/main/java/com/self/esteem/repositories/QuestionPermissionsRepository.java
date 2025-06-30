package com.self.esteem.repositories;

import com.self.esteem.entities.QuestionsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionPermissionsRepository extends JpaRepository<QuestionsPermission,Long> {
    List<QuestionsPermission> findByQuestionIdAndUserId(Long questionId, Long userId);
    List<QuestionsPermission> findAllByUserId(Long userId);
    boolean existsByQuestionIdAndUserId(Long questionId, Long userId);
}
