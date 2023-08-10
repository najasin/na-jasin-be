package com.najasin.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.najasin.domain.body.entity.Body;
import com.najasin.domain.characterset.entity.CharacterSet;
import com.najasin.domain.expression.entity.Expression;
import com.najasin.domain.face.entity.Face;
import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.user.entity.enums.UserType;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.najasin.domain.user.entity.enums.Role;
import com.najasin.global.audit.AuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@Column(name = "user_id")
	private String id;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<Role> role;

	@Enumerated(EnumType.STRING)
	@Column(name = "last_type")
	private UserType lastType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_set", referencedColumnName = "set_id")
	private CharacterSet set;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "face_id", referencedColumnName = "face_id")
	private Face face;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "body_id", referencedColumnName = "body_id")
	private Body body;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expression_id", referencedColumnName = "expression_id")
	private Expression expression;

	@OneToMany(mappedBy = "user")
	private List<UserKeyword> userKeywords;

	@Embedded
	private Oauth2Entity oauth2Entity;

	@Embedded
	private AuditEntity auditEntity;

	public User(String id, Oauth2Entity oauth2Entity) {
		this.id = id;
		this.oauth2Entity = oauth2Entity;
		this.role = new ArrayList<>(List.of(Role.ROLE_MEMBER));
		this.auditEntity = new AuditEntity();
	}

	public List<SimpleGrantedAuthority> getRole() {
		return role.stream()
				.map(Role::name)
				.map(SimpleGrantedAuthority::new)
				.toList();
	}
}
