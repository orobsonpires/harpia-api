/**
 * 
 */
package com.robsonliebke.harpia.authenticator.entity;

/**
 * @author robsonliebke
 *
 */
public class AuthenticationResult {

	private boolean successful;

	private String jwtToken;

	public AuthenticationResult(boolean successful, String jwtToken) {
		super();
		this.successful = successful;
		this.jwtToken = jwtToken;
	}

	public AuthenticationResult() {
		super();
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
