package com.najasin.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.manual.answer.entity.Answer;
import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.manual.userKeyword.entity.UserKeyword;
import com.najasin.domain.user.entity.userType.UserUserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.najasin.domain.user.entity.enums.Role;
import com.najasin.global.audit.AuditEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@Column(name = "user_id")
	private String id;

	@Column(name = "nickname")
	private String nickname;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<Role> role;


	// @OneToMany(mappedBy = "user")
	// private List<UserKeyword> userKeywords;
	//
	// @OneToMany(mappedBy = "user")
	// private List<Answer> answers;
	//
	// @OneToMany(mappedBy = "user")
	// private List<Comment> comments;

	@OneToMany(mappedBy = "user")
	private List<UserUserType> userUserTypes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_user_type", referencedColumnName = "user_type_id")
	private UserType lastUserType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "face_id", referencedColumnName = "face_id")
	private Face face;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expression_id", referencedColumnName = "expression_id")
	private Expression expression;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "body_id", referencedColumnName = "body_id")
	private Body body;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "set_id", referencedColumnName = "set_id")
	private CharacterSet set;

	@Embedded
	private Oauth2Entity oauth2Entity;

	@Embedded
	private AuditEntity auditEntity;

	public User(String id, Oauth2Entity oauth2Entity) {
		this.id = id;
		this.oauth2Entity = oauth2Entity;
		this.role = new ArrayList<>(List.of(Role.ROLE_MEMBER));

		this.auditEntity = new AuditEntity();
		// userKeywords = new ArrayList<>();
		// answers = new ArrayList<>();
		// comments = new ArrayList<>();
		userUserTypes = new ArrayList<>();
	}

	public void updateLastUserType(UserType lastUserType) {
		this.lastUserType = lastUserType;
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

	// public void updateAnswer(List<Answer> answers) {
	// 	this.answers = answers;
	// }

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	// public void updateUserUserType(UserUserType userUserType) {
	// 	for (UserUserType uut : this.userUserTypes) {
	// 		if (uut.getUserType() == userUserType.getUserType()) {
	// 			this.userUserTypes.remove(uut);
	// 			break;
	// 		}
	// 	}
	// 	this.userUserTypes.add(userUserType);
	// }
	//
	// public void deleteKeywords(){
	// 	this.userKeywords = new ArrayList<>();}
	//
	// public void updateKeyword(UserKeyword userKeyword) {
	// 	this.userKeywords.add(userKeyword);
	// }

	public List<SimpleGrantedAuthority> getRole() {
		return role.stream()
				.map(Role::name)
				.map(SimpleGrantedAuthority::new)
				.toList();
	}
}
