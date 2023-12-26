package com.backend.system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.system.model.Message;
import com.backend.system.model.User;
import com.backend.system.services.MessageService;
import com.backend.system.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	@PostMapping("/messages/chat/{chatId}")
	public Message createMessage(@RequestBody Message request, @PathVariable Long chatId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Message message = messageService.createMessage(user, chatId, request);

		return message;
	}

	@GetMapping("/messages/chat/{chatId}")
	public List<Message> findChatMessages( @PathVariable Long chatId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		List<Message> messages = messageService.findChatMessages(chatId);

		return messages;
	}

}
