package com.example.englishmaster_be.helper;

import com.example.englishmaster_be.common.constant.QuestionTypeEnum;
import com.example.englishmaster_be.domain.mock_test.dto.request.MockTestPartRequest;
import com.example.englishmaster_be.model.answer.AnswerEntity;
import com.example.englishmaster_be.model.part.PartEntity;
import com.example.englishmaster_be.model.question.QuestionEntity;
import com.example.englishmaster_be.model.topic.TopicEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QuestionHelper {

    public static List<QuestionEntity> shuffleQuestionsAndAnswers(List<QuestionEntity> questionParentsList, PartEntity partEntity) {

        if(questionParentsList == null || partEntity == null) return null;

        questionParentsList = new ArrayList<>(questionParentsList);

        Collections.shuffle(questionParentsList);

        List<String> partTypesNotShuffle = List.of("Picture Description", "Question - Response", "Words Fill Completion", "Words Matching");

        boolean notShuffleAnswer = partTypesNotShuffle.stream().anyMatch(
                partType -> partType.equalsIgnoreCase(partEntity.getPartType())
        );

        boolean partTypeIsTextCompletion = partEntity.getPartType().equalsIgnoreCase("Text Completion");

        questionParentsList.forEach(questionEntity -> {

            if(questionEntity.getQuestionGroupChildren() != null) {

                List<QuestionEntity> questionsList4Shuffle = new ArrayList<>(questionEntity.getQuestionGroupChildren());

                if(!partTypeIsTextCompletion)
                    Collections.shuffle(questionsList4Shuffle);

                questionsList4Shuffle.forEach(
                        questionGroupChildEntity -> {

                            if (questionGroupChildEntity.getQuestionGroupChildren() != null)
                                questionGroupChildEntity.setQuestionGroupChildren(null);

                            if(!notShuffleAnswer){

                                if(questionGroupChildEntity.getAnswers() != null){

                                    List<AnswerEntity> answersList4Shuffle = new ArrayList<>(questionGroupChildEntity.getAnswers());

                                    Collections.shuffle(answersList4Shuffle);

                                    questionGroupChildEntity.setAnswers(answersList4Shuffle);
                                }
                            }

                        }
                );

                questionEntity.setQuestionGroupChildren(questionsList4Shuffle);
            }

        });

        return questionParentsList;
    }

    public static int totalQuestionChildOfParents(List<QuestionEntity> questionParents, TopicEntity topicEntity) {

        if(questionParents == null || topicEntity == null) return 0;

        return questionParents.stream()
                .filter(
                        questionEntity -> questionEntity != null && questionEntity.getTopics().contains(topicEntity)
                )
                .map(
                questionEntity -> {
                    if(questionEntity.getQuestionType().equals(QuestionTypeEnum.Words_Matching))
                        return 1;

                    return questionEntity.getQuestionGroupChildren().size();
                }
        ).reduce(0, Integer::sum);
    }

    public static int totalQuestionChildOfParent(QuestionEntity questionParent){

        if(questionParent == null) return 0;

        if(questionParent.getQuestionGroupChildren() == null) return 0;

        return questionParent.getQuestionGroupChildren().size();
    }

    public static int totalQuestionChildOfParts(List<PartEntity> partEntityList, TopicEntity topicEntity){

        if(partEntityList == null || topicEntity == null) return 0;

        return partEntityList.stream()
                .filter(Objects::nonNull)
                .map(
                    PartEntity::getQuestions
                )
                .filter(Objects::nonNull)
                .map(
                        questionEntityList -> totalQuestionChildOfParents(questionEntityList, topicEntity)
                ).reduce(0, Integer::sum);
    }

    public static int totalScoreQuestionsParent(List<QuestionEntity> questionEntityList, TopicEntity topicEntity){

        if(questionEntityList == null) return 0;

        return questionEntityList.stream()
                .filter(Objects::nonNull)
                .filter(
                        questionEntity -> questionEntity.getIsQuestionParent().equals(Boolean.TRUE)
                        && questionEntity.getTopics().contains(topicEntity)
                )
                .map(QuestionEntity::getQuestionScore)
                .reduce(0, Integer::sum);
    }

}
