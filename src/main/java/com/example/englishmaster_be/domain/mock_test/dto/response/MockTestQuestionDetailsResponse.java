package com.example.englishmaster_be.domain.mock_test.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockTestQuestionDetailsResponse{

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

    List<MockTestPartQuestionResponse> parts;

}
