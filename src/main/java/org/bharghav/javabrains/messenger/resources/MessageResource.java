package org.bharghav.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bharghav.javabrains.messenger.model.Message;
import org.bharghav.javabrains.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	MessageService messageService = new MessageService();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getMessages() {
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")// here message id comes in string format but "long" in line 28 converts it to long automatically
	@Produces(MediaType.APPLICATION_XML)
	public Message getMessage(@PathParam("messageId") long id) {
		
		return messageService.getMessage(id);
	}
	
}
