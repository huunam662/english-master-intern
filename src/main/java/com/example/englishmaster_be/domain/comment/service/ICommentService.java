package com.example.englishmaster_be.domain.comment.service;

import com.example.englishmaster_be.domain.comment.dto.request.CommentRequest;
import com.example.englishmaster_be.model.comment.CommentEntity;

import java.util.List;
import java.util.UUID;

public interface ICommentService {

    boolean checkCommentParent(CommentEntity comment);

    CommentEntity getCommentById(UUID commentID);

    List<CommentEntity> findAllByCommentParent(CommentEntity commentParent);

    List<CommentEntity> getListCommentByCommentId(UUID commentId);

    CommentEntity saveCommentToTopic(UUID topicId, CommentRequest commentRequest);

    CommentEntity saveCommentToPost(UUID postId, CommentRequest commentRequest);

    CommentEntity saveCommentToComment(UUID commentId, CommentRequest createCommentDTO);

    CommentEntity saveComment(UUID updateCommentId, CommentRequest commentRequest);

    void deleteComment(UUID commentId);
}
