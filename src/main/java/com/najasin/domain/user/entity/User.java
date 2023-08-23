package com.najasin.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.najasin.domain.user.entity.userType.UserType;
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

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<Role> role;

	@OneToMany(mappedBy = "user")
	private List<UserUserType> userUserTypes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "last_user_type", referencedColumnName = "user_type_id")
	private UserType lastUserType;

	@Embedded
	private Oauth2Entity oauth2Entity;

	@Embedded
	private AuditEntity auditEntity;

	public User(String id, Oauth2Entity oauth2Entity) {
		this.id = id;
		this.oauth2Entity = oauth2Entity;
		this.role = new ArrayList<>(List.of(Role.ROLE_MEMBER));

		this.auditEntity = new AuditEntity();
		userUserTypes = new ArrayList<>();
	}

	public void updateLastUserType(UserType lastUserType) {
		this.lastUserType = lastUserType;
	}

	public List<SimpleGrantedAuthority> getRole() {
		return role.stream()
				.map(Role::name)
				.map(SimpleGrantedAuthority::new)
				.toList();
	}
}
