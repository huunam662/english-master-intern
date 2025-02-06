package com.example.englishmaster_be.domain.part.dto.response;

import com.example.englishmaster_be.domain.question.dto.response.QuestionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartQuestionResponse {

    UUID partId;

    String partName;

    List<QuestionResponse> questions;
}
