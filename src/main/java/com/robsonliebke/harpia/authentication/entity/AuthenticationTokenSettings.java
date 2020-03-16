package com.robsonliebke.harpia.authentication.entity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.robsonliebke.harpia.configuration.Configurable;

/**
 * Settings for signing and verifying JWT tokens.
 * 
 * @author robsonliebke
 *
 */
@Dependent
public class AuthenticationTokenSettings {

	/**
	 * Secret for signing and verifying the token signature.
	 */
	@Inject
	@Configurable("authentication.jwt.secret")
	private String secret;

	/**
	 * Allowed clock skew for verifying the token signature (in seconds).
	 */
	@Inject
	@Configurable("authentication.jwt.clockSkew")
	private Long clockSkew;

	/**
	 * Identifies the recipients that the JWT token is intended for.
	 */
	@Inject
	@Configurable("authentication.jwt.audience")
	private String audience;

	/**
	 * Identifies the JWT token issuer.
	 */
	@Inject
	@Configurable("authentication.jwt.issuer")
	private String issuer;

	/**
	 * JWT claim for the roles.
	 */
	@Inject
	@Configurable("authentication.jwt.claimNames.roles")
	private String rolesClaimName = "roles";

	/**
	 * JWT claim for the token refreshment count.
	 */
	@Inject
	@Configurable("authentication.jwt.claimNames.refreshCount")
	private String refreshCountClaimName = "refreshCount";

	/**
	 * JWT claim for the maximum times that a token can be refreshed.
	 */
	@Inject
	@Configurable("authentication.jwt.claimNames.refreshLimit")
	private String refreshLimitClaimName = "refreshLimit";

	public String getSecret() {
		return secret;
	}

	public Long getClockSkew() {
		return clockSkew;
	}

	public String getAudience() {
		return audience;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getRolesClaimName() {
		return rolesClaimName;
	}

	public String getRefreshCountClaimName() {
		return refreshCountClaimName;
	}

	public String getRefreshLimitClaimName() {
		return refreshLimitClaimName;
	}
}
