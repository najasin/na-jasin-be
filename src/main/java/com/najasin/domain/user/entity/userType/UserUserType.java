package com.najasin.domain.user.entity.userType;

import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.user.entity.User;

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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "face_id", referencedColumnName = "face_id")
	private Face face;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expression_id", referencedColumnName = "expression_id")
	private Expression expression;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "body_id", referencedColumnName = "body_id")
	private Body body;

	@ManyToOne
	@JoinColumn(name = "character_set_id", referencedColumnName = "set_id")
	private CharacterSet set;

	@Column(name = "nickname")
	private String nickname;

	public UserUserType(User user, UserType userType, String nickname) {
		this.user = user;
		this.userType = userType;
		this.nickname = nickname;
	}

	public void updateCharacter(CharacterSet set) {
		this.set = set;

		this.face = null;
		this.body = null;
		this.expression = null;
	}

	public void updateCharacter(Face face, Body body, Expression expression) {
		this.face = face;
		this.body = body;
		this.expression = expression;

		this.set = null;
	}
}
