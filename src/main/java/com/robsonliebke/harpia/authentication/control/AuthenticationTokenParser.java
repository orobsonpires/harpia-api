package com.robsonliebke.harpia.authentication.control;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response.Status;

import com.robsonliebke.harpia.authentication.entity.AuthenticationTokenDetails;
import com.robsonliebke.harpia.exceptions.ApplicationException;
import com.robsonliebke.harpia.users.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;

/**
 * Controller which provides operations for parsing JWT tokens.
 * 
 * @author robsonliebke
 *
 */
@Dependent
public class AuthenticationTokenParser {

	@Inject
	private AuthenticationTokenSettings settings;

	/**
	 * Parse a JWT token.
	 *
	 * @param token
	 * @return
	 */
	public AuthenticationTokenDetails parseToken(String token) {

		try {

			Claims claims = Jwts.parser().setSigningKey(settings.getSecret()).requireAudience(settings.getAudience())
					.setAllowedClockSkewSeconds(settings.getClockSkew()).parseClaimsJws(token).getBody();

			return new AuthenticationTokenDetails.Builder().withId(extractTokenIdFromClaims(claims))
					.withUsername(extractUsernameFromClaims(claims)).withRoles(extractRolesFromClaims(claims))
					.withIssuedDate(extractIssuedDateFromClaims(claims))
					.withExpirationDate(extractExpirationDateFromClaims(claims))
					.withRefreshCount(extractRefreshCountFromClaims(claims))
					.withRefreshLimit(extractRefreshLimitFromClaims(claims)).build();

		} catch (ExpiredJwtException e) {
			throw new ApplicationException(Status.UNAUTHORIZED, "Expired token", e);
		} catch (InvalidClaimException e) {
			throw new ApplicationException(Status.UNAUTHORIZED, "Invalid value for claim \"" + e.getClaimName() + "\"",
					e);
		} catch (Exception e) {
			throw new ApplicationException(Status.UNAUTHORIZED, "Invalid token", e);
		}
	}

	/**
	 * Extract the token identifier from the token claims.
	 *
	 * @param claims
	 * @return Identifier of the JWT token
	 */
	private String extractTokenIdFromClaims(@NotNull Claims claims) {
		return (String) claims.get(Claims.ID);
	}

	/**
	 * Extract the username from the token claims.
	 *
	 * @param claims
	 * @return Username from the JWT token
	 */
	private String extractUsernameFromClaims(@NotNull Claims claims) {
		return claims.getSubject();
	}

	/**
	 * Extract the user roles from the token claims.
	 *
	 * @param claims
	 * @return User roles from the JWT token
	 */
	private Set<Role> extractRolesFromClaims(@NotNull Claims claims) {
		@SuppressWarnings("unchecked")
		final List<String> rolesAsString = (List<String>) claims.getOrDefault(settings.getRolesClaimName(),
				new ArrayList<>());
		return rolesAsString.stream().map(Role::valueOf).collect(Collectors.toSet());
	}

	/**
	 * Extract the issued date from the token claims.
	 *
	 * @param claims
	 * @return Issued date of the JWT token
	 */
	private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
		return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Extract the expiration date from the token claims.
	 *
	 * @param claims
	 * @return Expiration date of the JWT token
	 */
	private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
		return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Extract the refresh count from the token claims.
	 *
	 * @param claims
	 * @return Refresh count from the JWT token
	 */
	private int extractRefreshCountFromClaims(@NotNull Claims claims) {
		return (int) claims.get(settings.getRefreshCountClaimName());
	}

	/**
	 * Extract the refresh limit from the token claims.
	 *
	 * @param claims
	 * @return Refresh limit from the JWT token
	 */
	private int extractRefreshLimitFromClaims(@NotNull Claims claims) {
		return (int) claims.get(settings.getRefreshLimitClaimName());
	}
}
