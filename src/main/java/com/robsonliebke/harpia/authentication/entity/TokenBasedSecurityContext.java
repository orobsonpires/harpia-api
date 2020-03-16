package com.robsonliebke.harpia.authentication.entity;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.robsonliebke.harpia.users.entity.Role;

/**
 * {@link SecurityContext} implementation for token-based authentication to
 * provide access to related security information.
 * 
 * @author robsonliebke
 *
 */
public class TokenBasedSecurityContext implements SecurityContext {

	private AuthenticatedUserDetails authenticatedUserDetails;
	private AuthenticationTokenDetails authenticationTokenDetails;
	private final boolean secure;

	public TokenBasedSecurityContext(AuthenticatedUserDetails authenticatedUserDetails,
			AuthenticationTokenDetails authenticationTokenDetails, boolean secure) {
		this.authenticatedUserDetails = authenticatedUserDetails;
		this.authenticationTokenDetails = authenticationTokenDetails;
		this.secure = secure;
	}

	@Override
	public Principal getUserPrincipal() {
		return authenticatedUserDetails;
	}

	@Override
	public boolean isUserInRole(String s) {
		return authenticatedUserDetails.getRoles().contains(Role.valueOf(s));
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}

	public AuthenticationTokenDetails getAuthenticationTokenDetails() {
		return authenticationTokenDetails;
	}
}