package com.robsonliebke.harpia.providers;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.robsonliebke.harpia.authentication.boundary.AuthenticationTokenService;
import com.robsonliebke.harpia.authentication.entity.AuthenticatedUserDetails;
import com.robsonliebke.harpia.authentication.entity.AuthenticationTokenDetails;
import com.robsonliebke.harpia.authentication.entity.TokenBasedSecurityContext;
import com.robsonliebke.harpia.users.boundary.UsersService;
import com.robsonliebke.harpia.users.entity.User;

/**
 * JWT authentication filter.
 * 
 * @author robsonliebke
 *
 */
@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private AuthenticationTokenService authenticationTokenService;

	@Inject
	private UsersService userService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			final String authenticationToken = authorizationHeader.substring(7);
			handleTokenBasedAuthentication(authenticationToken, requestContext);
		}
	}

	private void handleTokenBasedAuthentication(String authenticationToken, ContainerRequestContext requestContext) {

		final AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService
				.parseToken(authenticationToken);
		final User user = userService.findByUserName(authenticationTokenDetails.getUsername());
		final AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getUsername(),
				user.getRoles());

		final SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails,
				authenticationTokenDetails, requestContext.getSecurityContext().isSecure());

		requestContext.setSecurityContext(securityContext);
	}

}
