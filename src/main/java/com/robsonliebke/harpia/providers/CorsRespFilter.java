/**
 * 
 */
package com.robsonliebke.harpia.providers;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Cross-origin requests are requests made from one domain to another... For
 * security reasons, browsers restrict such requests, which are made within
 * scripts. The solution would be Cross-Origin Resource Sharing (CORS); this
 * mechanism allows servers to publish the set of origins that are allowed for
 * making web browser request to it.
 * 
 * This provider is defined to add the Access-Control headers to the response.
 * 
 * @author robsonliebke
 *
 */
public class CorsRespFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
	}

}
