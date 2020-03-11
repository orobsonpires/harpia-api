package com.robsonliebke.harpia.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.robsonliebke.harpia.exceptions.ApplicationException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
	public Response toResponse(ApplicationException ex) {
		return Response.status(ex.getStatus()).entity(ex.getMessage()).build();
	}
}
