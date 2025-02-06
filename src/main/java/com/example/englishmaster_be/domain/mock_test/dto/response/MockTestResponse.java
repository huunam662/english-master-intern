package com.example.englishmaster_be.domain.mock_test.dto.response;

import com.example.englishmaster_be.domain.mock_test_result.dto.response.MockTestResultResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockTestResponse {

    UUID mockTestId;

    Integer totalScoreParts;

    Integer totalScoreCorrect;

    Integer totalQuestionsParts;

    Integer totalQuestionsFinish;

    Integer totalQuestionsSkip;

    Integer totalAnswersCorrect;

    Integer totalAnswersWrong;

    Float answersCorrectPercent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime workTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime finishTime;

    MockTestTopicResponse topic;

    List<MockTestResultResponse> mockTestResults;

}
