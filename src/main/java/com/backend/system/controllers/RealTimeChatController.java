package com.backend.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.backend.system.model.Message;

@Controller
public class RealTimeChatController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat/{groupId}")
	public Message sendToUser(@Payload Message message, @DestinationVariable String groupId) {
		try {
			System.out.println("Message Received" + message.getContent());
			simpMessagingTemplate.convertAndSendToUser(groupId, "/private", message);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(123);
			return null;
		}
	}

}
