package com.example.englishmaster_be.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BucketValue {


    @Value("${englishmaster.connect.student-nodejs}")
    String bucketNameStudentNodejs;

    @Value("${firebase.adminsdk.path}")
    String adminSdkPath;
}
