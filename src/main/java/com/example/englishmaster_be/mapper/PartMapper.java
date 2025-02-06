package com.example.englishmaster_be.mapper;


import com.example.englishmaster_be.domain.part.dto.request.PartRequest;
import com.example.englishmaster_be.domain.part.dto.response.PartBasicResponse;
import com.example.englishmaster_be.domain.part.dto.response.PartQuestionResponse;
import com.example.englishmaster_be.model.mock_test.MockTestEntity;
import com.example.englishmaster_be.model.mock_test_result.MockTestResultEntity;
import com.example.englishmaster_be.model.part.PartEntity;
import com.example.englishmaster_be.domain.part.dto.response.PartResponse;
import com.example.englishmaster_be.model.topic.TopicEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface PartMapper {

    PartMapper INSTANCE = Mappers.getMapper(PartMapper.class);

    PartEntity toPartEntity(PartRequest partDto);

    @Mapping(target = "totalQuestion", expression = "java(part.getQuestions() != null ? part.getQuestions().size() : 0)")
    PartResponse toPartResponse(PartEntity part);

    List<PartResponse> toPartResponseList(List<PartEntity> partList);

    PartBasicResponse toPartBasicResponse(PartEntity partEntity);

    List<PartBasicResponse> toPartBasicResponseList(List<PartEntity> partList);

    default List<String> toPartNameResponseList(List<PartEntity> partEntities) {

        partEntities = new ArrayList<>(
                partEntities.stream().collect(
                        Collectors.toMap(
                                PartEntity::getPartName,
                                part -> part,
                                (part1, part2) -> part1
                        )
                ).values());

        return partEntities.stream().map(PartEntity::getPartName).sorted(String::compareTo).toList();
    }

    @Mapping(target = "questions", expression = "java(QuestionMapper.INSTANCE.toQuestionResponseList(partEntity.getQuestions(), topicEntity, partEntity))")
    PartQuestionResponse toPartQuestionResponse(PartEntity partEntity, TopicEntity topicEntity);

    default List<PartQuestionResponse> toPartQuestionResponseList(List<PartEntity> partEntityList, TopicEntity topicEntity) {

        if(partEntityList == null) return null;

        return partEntityList.stream()
                .filter(Objects::nonNull)
                .map(
                        partEntity -> toPartQuestionResponse(partEntity, topicEntity)
                )
                .toList();
    }

    @Mapping(target = "partId", ignore = true)
    void flowToPartEntity(PartRequest partRequest, @MappingTarget PartEntity partEntity);

}
