package com.example.englishmaster_be.domain.admin.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountMockTestTopicResponse {

    String topicName;

    int countMockTest;

}
