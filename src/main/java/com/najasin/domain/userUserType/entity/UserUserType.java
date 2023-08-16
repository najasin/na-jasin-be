package com.najasin.domain.userUserType.entity;

import com.najasin.domain.body.entity.Body;
import com.najasin.domain.characterset.entity.CharacterSet;
import com.najasin.domain.expression.entity.Expression;
import com.najasin.domain.face.entity.Face;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userType.entity.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_userType")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(UserUserTypeId.class)
public class UserUserType {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id", insertable = false, updatable = false)
    private UserType userType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "character_set_id", referencedColumnName = "set_id")
    private CharacterSet set;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "face_id", referencedColumnName = "face_id")
    private Face face;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "body_id", referencedColumnName = "body_id")
    private Body body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expression_id", referencedColumnName = "expression_id")
    private Expression expression;

    public void updateCharacter(Face face, Body body, Expression expression, CharacterSet characterSet) {
        this.face = face;
        this.body = body;
        this.expression = expression;
        this.set = characterSet;
    }
}
