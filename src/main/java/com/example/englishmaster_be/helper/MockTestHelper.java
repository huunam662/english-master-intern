package com.example.englishmaster_be.helper;

import com.example.englishmaster_be.common.constant.sort.SortByMockTestFieldsEnum;
import com.example.englishmaster_be.domain.mock_test.dto.request.MockTestPartRequest;
import com.example.englishmaster_be.domain.mock_test.dto.response.MockTestTotalCountResponse;
import com.example.englishmaster_be.model.mock_test.QMockTestEntity;
import com.example.englishmaster_be.model.mock_test_detail.MockTestDetailEntity;
import com.example.englishmaster_be.model.mock_test_result.MockTestResultEntity;
import com.example.englishmaster_be.model.part.PartEntity;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

public class MockTestHelper {


    public static int totalQuestionOfPart(MockTestPartRequest mockTestPartRequest) {

        if(mockTestPartRequest == null) return 0;

        if(mockTestPartRequest.getQuestionParentAnswers() == null) return 0;

        return mockTestPartRequest.getQuestionParentAnswers().stream()
                .filter(Objects::nonNull)
                .map(
                questionParentRequest -> {

                    if(questionParentRequest.getQuestionChildrenAnswers() == null)
                        return 0;

                    return questionParentRequest.getQuestionChildrenAnswers().size();
                }
        ).reduce(0, Integer::sum);
    }

    public static int totalQuestionOfPartRequestList(
            List<MockTestPartRequest> mockTestPartRequestList
    ){

        if(mockTestPartRequestList == null) return 0;

        return mockTestPartRequestList.stream()
                .filter(Objects::nonNull)
                .map(
                MockTestHelper::totalQuestionOfPart
        ).reduce(0, Integer::sum);
    }

    public static void taskForCorrectAnswerOfMockTest(
            MockTestDetailEntity mockTestDetailEntity,
            MockTestTotalCountResponse mockTestTotalCountResponse,
            int scoreCorrect
    ){

        mockTestTotalCountResponse.setTotalAnswersCorrect(
                mockTestTotalCountResponse.getTotalAnswersCorrect() + 1
        );

        mockTestTotalCountResponse.setTotalScoreCorrect(
                mockTestTotalCountResponse.getTotalScoreCorrect() + scoreCorrect
        );

        mockTestDetailEntity.setScoreAchieved(scoreCorrect);
    }

    public static void taskForIncorrectAnswerOfMockTest(
            MockTestDetailEntity mockTestDetailEntity,
            MockTestTotalCountResponse mockTestTotalCountResponse
    ){

        mockTestTotalCountResponse.setTotalAnswersWrong(
                mockTestTotalCountResponse.getTotalAnswersWrong() + 1
        );

        mockTestDetailEntity.setScoreAchieved(0);
    }

    public static OrderSpecifier<?> buildMockTestOrderSpecifier(
            SortByMockTestFieldsEnum sortBy,
            Sort.Direction sortDirection
    ){

        boolean isAscending = sortDirection != null && sortDirection.isAscending();

        return switch (sortBy){
            case Correct_Percent -> isAscending ? QMockTestEntity.mockTestEntity.answersCorrectPercent.asc() : QMockTestEntity.mockTestEntity.answersCorrectPercent.desc();
            case Answers_Correct -> isAscending ? QMockTestEntity.mockTestEntity.totalAnswersCorrect.asc() : QMockTestEntity.mockTestEntity.totalAnswersCorrect.desc();
            case Answers_Wrong -> isAscending ? QMockTestEntity.mockTestEntity.totalAnswersWrong.asc() : QMockTestEntity.mockTestEntity.totalAnswersWrong.desc();
            case Questions_Finish -> isAscending ? QMockTestEntity.mockTestEntity.totalQuestionsFinish.asc() : QMockTestEntity.mockTestEntity.totalQuestionsFinish.desc();
            case Questions_Work -> isAscending ? QMockTestEntity.mockTestEntity.totalQuestionsParts.asc() : QMockTestEntity.mockTestEntity.totalQuestionsParts.desc();
            default -> isAscending ? QMockTestEntity.mockTestEntity.updateAt.asc() : QMockTestEntity.mockTestEntity.updateAt.desc();
        };
    }

}
