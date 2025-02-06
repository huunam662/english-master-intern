package com.example.englishmaster_be.model.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, UUID> {

    @Query("Select f FROM FeedbackEntity f WHERE f.id = :feedbackId")
    Optional<FeedbackEntity> findByFeedbackId(UUID feedbackId);
}
