package com.example.englishmaster_be.model.question;

import com.example.englishmaster_be.model.part.PartEntity;
import com.example.englishmaster_be.model.topic.TopicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {

    Optional<QuestionEntity> findByQuestionId(UUID questionId);

    List<QuestionEntity> findAllByQuestionGroupParent(QuestionEntity question);

    Page<QuestionEntity> findAllByQuestionGroupParentAndPart(QuestionEntity question, PartEntity part, Pageable pageable);

    List<QuestionEntity> findByTopicsAndPart(TopicEntity topic, PartEntity part);

    Page<QuestionEntity> findAll(Pageable pageable);

    int countByQuestionGroupParent(QuestionEntity question);

    boolean existsByQuestionGroupParent(QuestionEntity question);

    List<QuestionEntity> findByPart(PartEntity part);


}
