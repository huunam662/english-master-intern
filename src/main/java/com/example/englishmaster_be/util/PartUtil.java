package com.example.englishmaster_be.util;

import com.example.englishmaster_be.domain.mock_test.dto.request.MockTestPartRequest;
import com.example.englishmaster_be.exception.template.BadRequestException;
import com.example.englishmaster_be.model.mock_test_result.MockTestResultEntity;
import com.example.englishmaster_be.model.part.PartEntity;
import com.example.englishmaster_be.model.question.QuestionEntity;
import com.example.englishmaster_be.model.topic.TopicEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PartUtil {

    public static List<UUID> convertToPartUUID(List<MockTestPartRequest> mockTestPartRequestList){

        if(mockTestPartRequestList == null)
            throw new BadRequestException("parts list from Mock test is null");

        return mockTestPartRequestList.stream().map(
                MockTestPartRequest::getPartId
        ).toList();
    }

    public static int totalScoreOfPart(PartEntity partEntity, TopicEntity topicEntity){

        if(partEntity == null || topicEntity == null) return 0;

        if(partEntity.getQuestions() == null) return 0;

        return QuestionUtil.totalScoreQuestionsParent(partEntity.getQuestions(), topicEntity);
    }

    public static List<PartEntity> getAllPartsFrom(List<MockTestResultEntity> mockTestResultEntityList){

        if(mockTestResultEntityList == null) return null;

        return mockTestResultEntityList.stream()
                .filter(Objects::nonNull)
                .map(
                        MockTestResultEntity::getPart
                )
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<PartEntity> filterAllQuestionsForPartsAndTopic(List<PartEntity> partEntityList, TopicEntity topicEntity){

        if(partEntityList == null || topicEntity == null) return null;

        return partEntityList.stream()
                .filter(
                        partEntity -> partEntity.getQuestions() != null
                )
                .peek(
                        partEntity -> {

                            List<QuestionEntity> questionEntityList = partEntity.getQuestions().stream()
                                    .filter(
                                            questionEntity -> questionEntity.getTopics().contains(topicEntity)
                                    )
                                    .toList();

                            partEntity.setQuestions(questionEntityList);
                        }
                )
                .toList();
    }

}
