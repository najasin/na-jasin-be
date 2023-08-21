package com.najasin.domain.manual.entity.comment;

import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.entity.User;
import com.najasin.global.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @Column(name = "content")
    private String content;

    @Column(name = "nickname")
    private String nickname;

    @Embedded
    private AuditEntity auditEntity;

    public AnswerParam toAnswerParam() {
        return new AnswerParam(question.getId(), question.getQuestion(), content);
    }
}
