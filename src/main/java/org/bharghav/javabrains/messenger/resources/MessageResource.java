package org.bharghav.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.bharghav.javabrains.messenger.model.Message;
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
	
	@GET// Adding query param annotation for retrieving the messages according to year parameter and also start and size of pagination
	public List<Message> getMessages(@QueryParam("year") int year,
									@QueryParam("start") int start,
									@QueryParam("size") int size) {
		if(year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if(start > 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)// JSON message is converted to Message instance by Jersey
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
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
	public Message getMessage(@PathParam("messageId") long id) {
		
		return messageService.getMessage(id);
	}
	
}
