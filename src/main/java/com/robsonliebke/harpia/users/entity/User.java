package com.robsonliebke.harpia.users.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author robsonliebke
 */
@Entity
@Table(name = "HARPIA_USER")
public class User {
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String username;
	@NotNull
	private String password;

	@NotNull
	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "security_role")
	private Set<Role> roles;

	// https://github.com/wildfly/quickstart/blob/master/jaxrs-jwt/service/src/main/java/org/jboss/quickstarts/jaxrsjwt/user/User.java
	// https://github.com/OpenLiberty/sample-async-rest/tree/master/src/main/java/io/openliberty/sample/async/rest/client/jaxrs21
	// https://github.com/hantsy/javaee8-jaxrs-sample/tree/master/backend/src/main/java/com/github/hantsy/ee8sample/security

	public User(String username, String password, Set<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public User() {

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
