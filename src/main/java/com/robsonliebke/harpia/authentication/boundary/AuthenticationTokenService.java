/**
 * 
 */
package com.robsonliebke.harpia.authentication.boundary;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import com.robsonliebke.harpia.authentication.control.AuthenticationTokenIssuer;
import com.robsonliebke.harpia.authentication.control.AuthenticationTokenParser;
import com.robsonliebke.harpia.authentication.entity.AuthenticationTokenDetails;
import com.robsonliebke.harpia.configuration.Configurable;
import com.robsonliebke.harpia.exceptions.ApplicationException;
import com.robsonliebke.harpia.users.entity.Role;

/**
 * 
 * Service which provides operations for JWT tokens.
 * 
 * @author robsonliebke
 */
@Stateless
public class AuthenticationTokenService {

	/**
	 * How many times the token can be refreshed.
	 */
	@Inject
	@Configurable("authentication.jwt.refreshLimit")
	private Integer refreshLimit;

	/**
	 * How long the token is valid for (in seconds).
	 */
	@Inject
	@Configurable("authentication.jwt.validFor")
	private Long validFor;

	@Inject
	private AuthenticationTokenParser tokenParser;

	@Inject
	private AuthenticationTokenIssuer tokenIssuer;

	/**
	 * Issue a token for a user with the given roles.
	 * 
	 * The JWT is separated into 3 sections, the header, payload and signature, e.g:
	 * <p>
	 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9 .
	 * 
	 * eyJzdWIiOiJ1c2Vycy9Uek1Vb2NNRjRwIiwibmFtZSI6IlJvYmVydCBUb2tlbiBNYW4iLCJzY29wZSI6InNlbGYgZ3JvdXBzL2FkbWlucyIsImV4cCI6IjEzMDA4MTkzODAifQ
	 * . 1pVOLQduFWW3muii1LExVBt2TK1-MdRI4QjhKryaDwc
	 * </p>
	 * 
	 * <p>
	 * In this example, Section 1 is a header which describes the token. Section 2
	 * is the payload, which contains the JWT’s claims, and Section 3 is the
	 * signature hash that can be used to verify the integrity of the token (if you
	 * have the secret key that was used to sign it).
	 * </p>
	 * 
	 * @param username
	 * @param roles
	 * 
	 * @return {@link String} representing JSON Web Token.
	 */
	public String issueToken(String username, Set<Role> roles) {

		final String id = generateTokenIdentifier();
		final ZonedDateTime issuedDate = ZonedDateTime.now();
		final ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

		final AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
				.withId(id).withUsername(username).withRoles(roles).withIssuedDate(issuedDate)
				.withExpirationDate(expirationDate).withRefreshCount(0).withRefreshLimit(refreshLimit).build();

		return tokenIssuer.issueToken(authenticationTokenDetails);
	}

	/**
	 * Refresh a token.
	 *
	 * @param currentTokenDetails
	 * @return {@link String} representing refreshed JSON Web Token.
	 */
	public String refreshToken(final AuthenticationTokenDetails currentTokenDetails) {

		if (!currentTokenDetails.isEligibleForRefreshment()) {
			throw new ApplicationException(Status.UNAUTHORIZED, "This token cannot be refreshed");
		}

		final ZonedDateTime issuedDate = ZonedDateTime.now();
		final ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

		final AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
				.withId(currentTokenDetails.getId()) // Reuse the same id
				.withUsername(currentTokenDetails.getUsername()).withRoles(currentTokenDetails.getRoles())
				.withIssuedDate(issuedDate).withExpirationDate(expirationDate)
				.withRefreshCount(currentTokenDetails.getRefreshCount() + 1).withRefreshLimit(refreshLimit).build();

		return tokenIssuer.issueToken(newTokenDetails);
	}

	/**
	 * Parse and validate the token.
	 *
	 * @param token
	 * @return
	 */
	public AuthenticationTokenDetails parseToken(String token) {
		return tokenParser.parseToken(token);
	}

	/**
	 * Calculate the expiration date for a token.
	 *
	 * @param issuedDate
	 * @return the issue date({@link ZonedDateTime}) passed plus the amount of
	 *         seconds defined on the application.properties.
	 */
	private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
		return issuedDate.plusSeconds(validFor);
	}

	/**
	 * Generate a token identifier.
	 *
	 * @return random {@link UUID} as {@link String}
	 */
	private String generateTokenIdentifier() {
		return UUID.randomUUID().toString();
	}

}
