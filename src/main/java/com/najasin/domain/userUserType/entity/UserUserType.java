package com.najasin.domain.userUserType.entity;

import com.najasin.domain.character.body.entity.Body;
import com.najasin.domain.character.characterset.entity.CharacterSet;
import com.najasin.domain.character.expression.entity.Expression;
import com.najasin.domain.character.face.entity.Face;
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

    @ManyToOne
    @JoinColumn(name = "character_set_id", referencedColumnName = "set_id")
    private CharacterSet set;

    @ManyToOne
    @JoinColumn(name = "face_id", referencedColumnName = "face_id")
    private Face face;

    @ManyToOne
    @JoinColumn(name = "body_id", referencedColumnName = "body_id")
    private Body body;

    @ManyToOne
    @JoinColumn(name = "expression_id", referencedColumnName = "expression_id")
    private Expression expression;

    public UserUserType(User user, UserType userType) {
        this.user = user;
        this.userType = userType;
    }

    public void updateCharacter(Face face, Body body, Expression expression, CharacterSet characterSet) {
        this.face = face;
        this.body = body;
        this.expression = expression;
        this.set = characterSet;
    }
}
