package com.example.englishmaster_be.shared.unsplash.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnsplashSearchResponse {

    List<UnsplashResultResponse> results;

}

