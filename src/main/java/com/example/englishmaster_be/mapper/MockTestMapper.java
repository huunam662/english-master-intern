package com.example.englishmaster_be.mapper;

import com.example.englishmaster_be.domain.mock_test.dto.request.MockTestRequest;
import com.example.englishmaster_be.domain.mock_test.dto.response.*;
import com.example.englishmaster_be.helper.QuestionHelper;
import com.example.englishmaster_be.model.answer.AnswerEntity;
import com.example.englishmaster_be.model.mock_test.MockTestEntity;
import com.example.englishmaster_be.model.mock_test_result.MockTestResultEntity;
import com.example.englishmaster_be.model.part.PartEntity;
import com.example.englishmaster_be.model.question.QuestionEntity;
import com.example.englishmaster_be.model.topic.TopicEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mapper(builder = @Builder(disableBuilder = true), imports = {QuestionHelper.class})
public interface MockTestMapper {

    MockTestMapper INSTANCE = Mappers.getMapper(MockTestMapper.class);

    @Mapping(target = "topic", expression = "java(toMockTestTopicResponse(mockTestEntity.getTopic()))")
    @Mapping(target = "mockTestResults", expression = "java(MockTestResultMapper.INSTANCE.toMockTestResultResponseList(mockTestEntity.getMockTestResults()))")
    MockTestResponse toMockTestResponse(MockTestEntity mockTestEntity);

    List<MockTestResponse> toMockTestResponseList(List<MockTestEntity> mockTestEntityList);

    MockTestEntity toMockTestEntity(MockTestRequest mockTestRequest);

    MockTestPartResponse toMockTestPartResponse(PartEntity partEntity);

    List<MockTestPartResponse> toMockTestPartResponseList(List<PartEntity> partEntityList);

    MockTestTopicResponse toMockTestTopicResponse(TopicEntity topicEntity);

    MockTestQuestionResponse toMockTestQuestionResponse(QuestionEntity questionEntity);

    List<MockTestQuestionResponse> toMockTestQuestionResponseList(List<QuestionEntity> questionEntityList);

    @Mapping(target = "topic", expression = "java(toMockTestTopicResponse(mockTestEntity.getTopic()))")
    @Mapping(target = "parts", expression = "java(toMockTestPartResponseListFrom(mockTestEntity.getMockTestResults()))")
    MockTestFilterResponse toMockTestFilterResponse(MockTestEntity mockTestEntity);

    default List<MockTestPartResponse> toMockTestPartResponseListFrom(List<MockTestResultEntity> mockTestResultEntityList) {

        if(mockTestResultEntityList == null) return null;

        return mockTestResultEntityList.stream()
                .filter(Objects::nonNull)
                .map(
                        mockTestResultEntity -> toMockTestPartResponse(mockTestResultEntity.getPart())
                )
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(MockTestPartResponse::getPartName))
                .toList();
    }

    List<MockTestFilterResponse> toMockTestFilterResponseList(List<MockTestEntity> mockTestEntityList);

    @Mapping(target = "contents", expression = "java(ContentMapper.INSTANCE.toContentBasicResponseList(questionChildren.getContentCollection()))")
    @Mapping(target = "answers", expression = "java(AnswerMapper.INSTANCE.toAnswerResponseList(questionChildren.getAnswers()))")
    @Mapping(target = "answerChoice", expression = "java(AnswerMapper.INSTANCE.toAnswerResponse(answerChoice))")
    MockTestQuestionChildResponse toMockTestQuestionChildResponse(QuestionEntity questionChildren, AnswerEntity answerChoice);

    @Mapping(target = "numberOfQuestionsChild", expression = "java(QuestionHelper.totalQuestionChildOfParent(questionParent))")
    MockTestQuestionParentResponse toMockTestQuestionParentResponse(QuestionEntity questionParent);

    @Mapping(target = "mockTestId", source = "mockTest.mockTestId")
    @Mapping(target = "part", expression = "java(toMockTestPartResponse(mockTestResultEntity.getPart()))")
    MockTestPartQuestionResponse toMockTestPartQuestionResponse(MockTestResultEntity mockTestResultEntity);

    @Mapping(target = "topic", expression = "java(toMockTestTopicResponse(mockTestEntity.getTopic()))")
    MockTestQuestionDetailsResponse toMockTestQuestionDetailsResponse(MockTestEntity mockTestEntity);

}
