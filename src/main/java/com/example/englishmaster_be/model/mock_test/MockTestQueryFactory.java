package com.example.englishmaster_be.model.mock_test;

import com.example.englishmaster_be.model.user.UserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MockTestQueryFactory {

    JPAQueryFactory jpaQueryFactory;

    private JPAQuery<MockTestEntity> selectFromMockTestEntity(){

        return jpaQueryFactory.selectFrom(QMockTestEntity.mockTestEntity);
    }

    public Optional<MockTestEntity> findMockTestByIdAndUser(
            UUID mockTestId,
            UserEntity userEntity
    ){

        BooleanExpression conditionQueryPattern = QMockTestEntity.mockTestEntity.mockTestId.eq(mockTestId)
                .and(
                        QMockTestEntity.mockTestEntity.user.eq(userEntity)
                );

        MockTestEntity mockTestResult = selectFromMockTestEntity().where(conditionQueryPattern).fetchOne();

        return Optional.ofNullable(mockTestResult);
    }

}
