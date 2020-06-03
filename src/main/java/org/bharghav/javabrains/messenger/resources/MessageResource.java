package org.bharghav.javabrains.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.bharghav.javabrains.messenger.model.Message;
import org.bharghav.javabrains.messenger.resources.beans.MessageFilterBean;
import org.bharghav.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)//Produces and Consumes are done at class level to be applied for each method
@Produces(MediaType.APPLICATION_JSON)// Infact these two can be written for each method individually but since every method consumes or produces this is efficient
public class MessageResource {
	
	MessageService messageService = new MessageService();

//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public List<Message> getMessages() {
//		return messageService.getAllMessages();
//	}
//	
//	@GET
//	@Path("/{messageId}")// here message id comes in string format but "long" in line 28 converts it to long automatically
//	@Produces(MediaType.APPLICATION_XML)
//	public Message getMessage(@PathParam("messageId") long id) {
//		
//		return messageService.getMessage(id);
//	}
	
	//@GET// Adding query param annotation for retrieving the messages according to year parameter and also start and size of pagination
//	public List<Message> getMessages(@QueryParam("year") int year,
//									@QueryParam("start") int start,
//									@QueryParam("size") int size) {
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {

		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() > 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)// JSON message is converted to Message instance by Jersey
//	public Message addMessage(Message message) {
//		return messageService.addMessage(message);
//	}
	
	
	//Controlling the response and sending a status code of 201 when a resource is created and a location header for the uri of the new message created
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = 		uriInfo.getAbsolutePathBuilder().path(newId).build();

		//building a new instance of a response and returning
//			return Response.status(Status.CREATED)
//					.entity(newMessage)
//					.build();
		
		return Response.created(uri)
						.entity(newMessage)
						.build();
				
	}
	
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")// here message id comes in string format but "long" in line 28 converts it to long automatically
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);

		String uri = getUriForSelf(uriInfo, message);
		
		message.addLink(uri, "self"); // adding a link to self or a link to resource itself
		//return messageService.getMessage(id);
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")//"/{messageId}/comments" when this path matches in the url then the below method is executed and see the return type is another resource and looks into that resource for the return method
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
