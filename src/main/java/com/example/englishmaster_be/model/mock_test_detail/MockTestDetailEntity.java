package com.example.englishmaster_be.model.mock_test_detail;

import com.example.englishmaster_be.model.answer.AnswerEntity;
import com.example.englishmaster_be.model.question.QuestionEntity;
import com.example.englishmaster_be.model.mock_test_result.MockTestResultEntity;
import com.example.englishmaster_be.model.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "mock_test_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockTestDetailEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
    UUID mockTestDetailId;

    @Column(name = "answer_content")
    String answerContent;

    @Column(name = "answer_correct_content")
    String answerCorrectContent;

    @Column(name = "is_correct_answer")
    Boolean isCorrectAnswer;

    @Column(name = "score_achieved")
    Integer scoreAchieved;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "update_at")
	LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "result_mock_test_id", referencedColumnName = "id")
    MockTestResultEntity resultMockTest;

    @ManyToOne
    @JoinColumn(name = "question_child_id", referencedColumnName = "id")
    QuestionEntity questionChild;

    @ManyToOne
    @JoinColumn(name = "answer_choice_id", referencedColumnName = "id")
    AnswerEntity answerChoice;

    @ManyToOne
    @JoinColumn(name = "answer_correct_id", referencedColumnName = "id")
    AnswerEntity answerCorrect;

    @ManyToOne
    @JoinColumn(name = "create_by", referencedColumnName = "id")
    UserEntity userCreate;

    @ManyToOne
    @JoinColumn(name = "update_by", referencedColumnName = "id")
    UserEntity userUpdate;


    @PrePersist
    void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updateAt = LocalDateTime.now();
    }
}
