package com.example.englishmaster_be.domain.cloudinary.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudairyUploadFileResponse {

	String url;

	String type;

}