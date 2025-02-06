package com.example.englishmaster_be.domain.mock_test.dto.response;

import com.example.englishmaster_be.common.constant.QuestionTypeEnum;
import com.example.englishmaster_be.domain.answer.dto.response.AnswerResponse;
import com.example.englishmaster_be.domain.content.dto.response.ContentBasicResponse;
import com.example.englishmaster_be.domain.question.dto.response.QuestionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockTestQuestionChildResponse {

    UUID questionId;

    String questionContent;

    String questionResult;

    String questionExplainEn;

    String questionExplainVn;

    String contentAudio;

    String contentImage;

    Integer questionScore;

    Integer numberChoice;

    Boolean isQuestionParent;

    QuestionTypeEnum questionType;

    List<ContentBasicResponse> contents;

    List<AnswerResponse> answers;

    AnswerResponse answerChoice;
}
