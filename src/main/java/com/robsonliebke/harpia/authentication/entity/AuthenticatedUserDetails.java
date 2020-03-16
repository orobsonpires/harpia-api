package com.robsonliebke.harpia.authentication.entity;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import com.robsonliebke.harpia.users.entity.Role;

public final class AuthenticatedUserDetails implements Principal {

	private final String username;
	private final Set<Role> roles;

	public AuthenticatedUserDetails(String username, Set<Role> roles) {
		this.username = username;
		this.roles = Collections.unmodifiableSet(roles);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public String getName() {
		return username;
	}
}
