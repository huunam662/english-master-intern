package com.example.englishmaster_be.domain.mock_test.dto.request;


import com.example.englishmaster_be.common.dto.request.FilterRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockTestFilterRequest extends FilterRequest {

    @Parameter(
            description = "Sort by field",
            example = "--",
            schema = @Schema(
                    allowableValues = {"latest", "correct-percent", "answers-correct", "answers-wrong", "questions-work", "questions-finish"}
            )
    )
    String sortBy;

    @Parameter(
            description = "Direction with [ACS or DESC]"
    )
    Sort.Direction sortDirection;

}
