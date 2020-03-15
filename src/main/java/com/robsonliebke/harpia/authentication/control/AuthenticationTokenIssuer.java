package com.robsonliebke.harpia.authentication.control;

import com.robsonliebke.harpia.authentication.entity.AuthenticationTokenDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Date;

/**
 * 
 * Control class which provides operations for issuing JWT tokens.
 * 
 * @author robsonliebke
 *
 */
@Dependent
public class AuthenticationTokenIssuer {

	@Inject
	private AuthenticationTokenSettings settings;

	/**
	 * Issue a JWT token
	 * 
	 * @param authenticationTokenDetails
	 * 
	 * @return a new JTW token based on {@link AuthenticationTokenDetails} passed.
	 */
	public String issueToken(AuthenticationTokenDetails authenticationTokenDetails) {
		return Jwts.builder().setId(authenticationTokenDetails.getId()).setIssuer(settings.getIssuer())
				.setAudience(settings.getAudience()).setSubject(authenticationTokenDetails.getUsername())
				.setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
				.setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
				.claim(settings.getRolesClaimName(), authenticationTokenDetails.getRoles())
				.claim(settings.getRefreshCountClaimName(), authenticationTokenDetails.getRefreshCount())
				.claim(settings.getRefreshLimitClaimName(), authenticationTokenDetails.getRefreshLimit())
				.signWith(SignatureAlgorithm.HS256, settings.getSecret()).compact();
	}

}
