/**
 * 
 */
package com.robsonliebke.harpia.exception;

import javax.ws.rs.core.Response.Status;

/**
 * @author robsonliebke
 *
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = -9079411854450419091L;

	private final Status status;

	public ApplicationException(Status status, String message) {
		this(status, message, null);
	}

	public ApplicationException(Status status, String message, Throwable error) {
		super(message, error);
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

}
