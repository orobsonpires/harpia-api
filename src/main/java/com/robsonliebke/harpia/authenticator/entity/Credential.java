/**
 * 
 */
package com.robsonliebke.harpia.authenticator.entity;

import javax.validation.constraints.NotNull;

/**
 * 
 * Class containing information used to authenticate actor.
 * 
 * @author robsonliebke
 *
 */
public class Credential {
	@NotNull
	private String username;
	
	@NotNull
	private String password;

	public Credential(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
