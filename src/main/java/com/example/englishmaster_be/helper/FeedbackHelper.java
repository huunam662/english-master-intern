package com.example.englishmaster_be.helper;

import com.example.englishmaster_be.common.constant.sort.SortByFeedbackFieldsEnum;

import com.example.englishmaster_be.model.feedback.QFeedbackEntity;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

public class FeedbackHelper {

    public static OrderSpecifier<?> buildFeedbackOrderSpecifier(SortByFeedbackFieldsEnum sortBy, Sort.Direction sortDirection) {

        boolean isAscending = sortDirection != null && sortDirection.isAscending();

        return switch (sortBy) {
            case Name -> isAscending ? QFeedbackEntity.feedbackEntity.name.asc() : QFeedbackEntity.feedbackEntity.name.desc();
            case Content -> isAscending ? QFeedbackEntity.feedbackEntity.content.asc() : QFeedbackEntity.feedbackEntity.content.desc();
            case Description -> isAscending ? QFeedbackEntity.feedbackEntity.description.asc() : QFeedbackEntity.feedbackEntity.description.desc();
            case CreateAt -> isAscending ? QFeedbackEntity.feedbackEntity.createAt.asc() : QFeedbackEntity.feedbackEntity.createAt.desc();
            default -> isAscending ? QFeedbackEntity.feedbackEntity.updateAt.asc() : QFeedbackEntity.feedbackEntity.updateAt.desc();
        };
    }

}
