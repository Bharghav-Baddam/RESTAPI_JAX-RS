package org.bharghav.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)//Produces and Consumes are done at class level to be applied for each method
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											@HeaderParam("customHeaderValue") String header,
											@CookieParam("name") String cookie) {
		return "Matrix Param : "+matrixParam+" Header Param : "+header+ "Cookie : "+cookie;
	}
	
	//Matrix Param example http://localhost:8080/messenger/webapi/injectdemo/annotations:param=value
	// customHeaderValue is a header in the headers
	// name is cookie name as can be seen in the postman
	
	// What if we dont know the names of cookies and we want to see all the cookies, all the headers..., follow below
	
	@GET
	@Path("context")// @Context is only for few special classes such as UriInfo
	public String getParamsUsingContext(@Context UriInfo uriInfo,@Context HttpHeaders headers) {
		
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "Path : "+ path + " Cookies: " + cookies;
	}
	
}
