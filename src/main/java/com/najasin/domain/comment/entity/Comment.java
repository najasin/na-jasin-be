package com.najasin.domain.comment.entity;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.user.entity.User;
import com.najasin.global.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@IdClass(CommentId.class)
public class Comment {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", insertable = false, updatable = false)
    private Question question;

    @Column(name = "comment")
    private String comment;

    @Column(name = "nickname")
    private String nickname;

    @Embedded
    private AuditEntity auditEntity;

    public Comment(User user, Question question, String comment, String nickname) {
        this.user = user;
        this.question = question;
        this.comment = comment;
        this.nickname = nickname;
        this.auditEntity = new AuditEntity();
    }
}
