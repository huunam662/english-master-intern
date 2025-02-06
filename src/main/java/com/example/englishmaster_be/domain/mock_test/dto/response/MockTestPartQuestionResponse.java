package com.example.englishmaster_be.domain.mock_test.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MockTestPartQuestionResponse {

    UUID mockTestResultId;

    UUID mockTestId;

    MockTestPartResponse part;

    List<MockTestQuestionParentResponse> questions;
}
