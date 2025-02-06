package com.example.englishmaster_be.domain.mock_test.dto.response;

import com.example.englishmaster_be.common.constant.QuestionTypeEnum;
import com.example.englishmaster_be.domain.content.dto.response.ContentBasicResponse;
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
public class MockTestQuestionParentResponse {

    UUID questionId;

    String questionContent;

    String questionResult;

    String questionExplainEn;

    String questionExplainVn;

    String contentAudio;

    String contentImage;

    Integer questionScore;

    Integer numberChoice;

    Integer numberOfQuestionsChild;

    Boolean isQuestionParent;

    QuestionTypeEnum questionType;

    List<ContentBasicResponse> contents;

    List<MockTestQuestionChildResponse> questionsChildren;
}
