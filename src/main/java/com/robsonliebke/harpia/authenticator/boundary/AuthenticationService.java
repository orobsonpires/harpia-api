/**
 * 
 */
package com.robsonliebke.harpia.authenticator.boundary;

import com.robsonliebke.harpia.authenticator.entity.Credential;

/**
 * @author robsonliebke
 *
 */
public class AuthenticationService {
	public boolean validateCredential(final Credential credential) {
		return true;
	}
	
	public boolean authenticate(final Credential credential) {
		return true;
	}
}
