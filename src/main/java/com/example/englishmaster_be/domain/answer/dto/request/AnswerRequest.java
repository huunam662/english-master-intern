package com.example.englishmaster_be.domain.answer.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerRequest {

    @Hidden
    UUID answerId;

    UUID questionId;

    String answerContent;

    String explainDetails;

    Boolean correctAnswer;

}
