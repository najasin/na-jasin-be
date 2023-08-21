package com.najasin.domain.user.entity.userType;

import com.najasin.domain.character.entity.CharacterSet;
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

	@ManyToOne
	@JoinColumn(name = "character_set_id", referencedColumnName = "set_id")
	private CharacterSet set;

	public UserUserType(User user, UserType userType) {
		this.user = user;
		this.userType = userType;
	}
}
