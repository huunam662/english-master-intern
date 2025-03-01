package com.example.englishmaster_be.domain.cloudinary.service;

import com.cloudinary.Cloudinary;
import com.example.englishmaster_be.domain.file_storage.dto.response.FileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService implements ICloudinaryService {

	Cloudinary cloudinary;

	@SneakyThrows
	public FileResponse uploadFile(MultipartFile file){

			Map uploadResultResponse = cloudinary.uploader().upload(file.getBytes(), Map.of());

			String imageUrl = String.valueOf(uploadResultResponse.get("url"));
//			String fileType = String.valueOf(uploadResultResponse.get("type"));

			return FileResponse.builder()
					.url(imageUrl)
					.type(file.getContentType())
					.build();
	}


}
