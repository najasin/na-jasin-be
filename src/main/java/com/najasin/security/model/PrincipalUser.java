package com.najasin.security.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.najasin.domain.user.entity.User;

public class PrincipalUser implements UserDetails, OidcUser, OAuth2User {
	private final User user;
	private final Map<String, Object> attribute;
	private final Collection<? extends GrantedAuthority> authorities;

	public PrincipalUser(User user, Map<String, Object> attribute, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.attribute = attribute;
		this.authorities = authorities;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getName() {
		return user.getId();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attribute;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return "password";
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getClaims() {
		return null;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return null;
	}
}
