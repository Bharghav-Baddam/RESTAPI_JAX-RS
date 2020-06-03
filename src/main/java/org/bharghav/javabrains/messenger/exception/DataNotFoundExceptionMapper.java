package org.bharghav.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.bharghav.javabrains.messenger.model.ErrorMessage;

@Provider //This registers this Mapper in JaxRS and makes it know that this takes care of mapping of exception and error message
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		// TODO Auto-generated method stub
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "https://bharghav,org");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}//Exception Mapper is a raw type and we need to convert it to a Generic type like DataNotFoundException  , therefor <DataNotFOundException>

}
