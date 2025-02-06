package com.example.englishmaster_be.domain.question.dto.response;

import com.example.englishmaster_be.domain.mock_test.dto.response.MockTestPartResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionMockTestDetailsResponse{

    MockTestPartResponse part;

}
