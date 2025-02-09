package com.example.englishmaster_be.helper;

import com.example.englishmaster_be.model.content.ContentEntity;
import com.example.englishmaster_be.model.content.QContentEntity;
import com.example.englishmaster_be.model.topic.TopicEntity;
import com.example.englishmaster_be.model.user.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class ContentHelper {

    JPAQueryFactory jpaQueryFactory;

    FileHelper fileHelper;

    public ContentEntity makeContentEntity(UserEntity byUser, TopicEntity refTopic, String contentData) {

        ContentEntity contentEntity = jpaQueryFactory.selectFrom(QContentEntity.contentEntity)
                .where(
                        QContentEntity.contentEntity.contentData.eq(contentData)
                                .and(QContentEntity.contentEntity.contentType.eq(fileHelper.mimeTypeFile(contentData)))
                )
                .fetchOne();

        if(contentEntity != null) return contentEntity;

        return ContentEntity.builder()
                .contentId(UUID.randomUUID())
                .contentData(contentData)
                .contentType(fileHelper.mimeTypeFile(contentData))
                .userCreate(byUser)
                .userUpdate(byUser)
                .topic(refTopic)
                .build();
    }

}
