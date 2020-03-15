package com.robsonliebke.harpia.authentication.entity;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.robsonliebke.harpia.users.entity.Role;

/**
 * Model that holds details about an authentication token.
 * 
 * @author robsonliebke
 *
 */
public final class AuthenticationTokenDetails {

	private final String id;
	private final String username;
	private final Set<Role> roles;
	private final ZonedDateTime issuedDate;
	private final ZonedDateTime expirationDate;
	private final int refreshCount;
	private final int refreshLimit;

	private AuthenticationTokenDetails(String id, String username, Set<Role> roles, ZonedDateTime issuedDate,
			ZonedDateTime expirationDate, int refreshCount, int refreshLimit) {
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.issuedDate = issuedDate;
		this.expirationDate = expirationDate;
		this.refreshCount = refreshCount;
		this.refreshLimit = refreshLimit;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public ZonedDateTime getIssuedDate() {
		return issuedDate;
	}

	public ZonedDateTime getExpirationDate() {
		return expirationDate;
	}

	public int getRefreshCount() {
		return refreshCount;
	}

	public int getRefreshLimit() {
		return refreshLimit;
	}

	/**
	 * Check if the authentication token is eligible for refreshment.
	 *
	 * @return
	 */
	public boolean isEligibleForRefreshment() {
		return refreshCount < refreshLimit;
	}

	/**
	 * Builder for the {@link AuthenticationTokenDetails}.
	 */
	public static class Builder {

		private String id;
		private String username;
		private Set<Role> roles;
		private ZonedDateTime issuedDate;
		private ZonedDateTime expirationDate;
		private int refreshCount;
		private int refreshLimit;

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withRoles(Set<Role> roles) {
			this.roles = Collections.unmodifiableSet(roles == null ? new HashSet<>() : roles);
			return this;
		}

		public Builder withIssuedDate(ZonedDateTime issuedDate) {
			this.issuedDate = issuedDate;
			return this;
		}

		public Builder withExpirationDate(ZonedDateTime expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder withRefreshCount(int refreshCount) {
			this.refreshCount = refreshCount;
			return this;
		}

		public Builder withRefreshLimit(int refreshLimit) {
			this.refreshLimit = refreshLimit;
			return this;
		}

		public AuthenticationTokenDetails build() {
			return new AuthenticationTokenDetails(id, username, roles, issuedDate, expirationDate, refreshCount,
					refreshLimit);
		}
	}
}
