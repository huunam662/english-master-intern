package com.example.englishmaster_be.util;

import com.example.englishmaster_be.model.answer.AnswerEntity;

import java.util.List;
import java.util.UUID;

public class AnswerUtil {

    public static UUID getIdCorrectAnswer(List<AnswerEntity> answers) {

        if(answers == null) return null;

        return answers.stream()
                .filter(
                answerEntity -> answerEntity.getCorrectAnswer().equals(Boolean.TRUE)
                )
                .map(AnswerEntity::getAnswerId)
                .findFirst()
                .orElse(null);

    }
}
