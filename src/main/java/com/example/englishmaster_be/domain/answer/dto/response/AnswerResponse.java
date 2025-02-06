package com.example.englishmaster_be.domain.answer.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerResponse {

    UUID questionId;

	UUID answerId;

	String answerContent;

    String explainDetails;

    Boolean correctAnswer;

}
