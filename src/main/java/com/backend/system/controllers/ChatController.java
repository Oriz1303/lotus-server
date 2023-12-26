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

import com.backend.system.exception.UserException;
import com.backend.system.model.Chat;
import com.backend.system.model.User;
import com.backend.system.requests.CreateChatRequest;
import com.backend.system.services.ChatService;
import com.backend.system.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
	@Autowired
	private ChatService chatService;
	@Autowired
	private UserService userService;

	@PostMapping("/chats")
	public Chat createChat(@RequestHeader("Authorization") String jwt, @RequestBody CreateChatRequest request)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		User partner = userService.findUserById(request.getPartnerId());
		Chat chat = chatService.createChat(user, partner);
		return chat;
	}

	@GetMapping("/chats")
	public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		List<Chat> chats = chatService.findUsersChat(user.getId());
		return chats;
	}

	@GetMapping("/chats/{chatId}")
	public Chat findChatById(@PathVariable Long chatId) throws Exception {
		Chat chat = chatService.findChatById(chatId);

		return chat;
	}

}
