package com.example.englishmaster_be.common.constant.sort;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SortByMockTestFieldsEnum {

    UpdateAt("latest"),
    Correct_Percent("correct-percent"),
    Answers_Correct("answers-correct"),
    Answers_Wrong("answers-wrong"),
    Questions_Work("questions-work"),
    Questions_Finish("questions-finish");

    String value;

    SortByMockTestFieldsEnum(String value) {

        if(value == null) value = "latest";

        this.value = value;
    }

    public static SortByMockTestFieldsEnum fromValue(String value){

        return Arrays.stream(SortByMockTestFieldsEnum.values()).filter(
                valueEnum -> valueEnum.value.equalsIgnoreCase(value)
                )
                .findFirst()
                .orElse(UpdateAt);
    }

}
