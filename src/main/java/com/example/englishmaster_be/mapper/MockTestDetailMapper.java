package com.example.englishmaster_be.mapper;

import com.example.englishmaster_be.domain.mock_test.dto.response.MockTestDetailResponse;
import com.example.englishmaster_be.model.mock_test_detail.MockTestDetailEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface MockTestDetailMapper {

    MockTestDetailMapper INSTANCE = Mappers.getMapper(MockTestDetailMapper.class);

    @Mapping(target = "questionChild", expression = "java(MockTestMapper.INSTANCE.toMockTestQuestionResponse(mockTestDetailEntity.getQuestionChild()))")
    @Mapping(target = "answerChoice", expression = "java(AnswerMapper.INSTANCE.toAnswerResponse(mockTestDetailEntity.getAnswerChoice()))")
    @Mapping(target = "answerCorrect", expression = "java(AnswerMapper.INSTANCE.toAnswerResponse(mockTestDetailEntity.getAnswerCorrect()))")
    MockTestDetailResponse toMockTestDetailResponse(MockTestDetailEntity mockTestDetailEntity);

    List<MockTestDetailResponse> toMockTestDetailResponseList(List<MockTestDetailEntity> mockTestDetailEntityList);

}
