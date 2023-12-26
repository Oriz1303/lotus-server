package com.backend.system.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.system.model.Chat;
import com.backend.system.model.Message;
import com.backend.system.model.User;
import com.backend.system.repositories.ChatRepository;
import com.backend.system.repositories.MessageRepository;
import com.backend.system.services.iservices.IMessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Message createMessage(User user, Long chatId, Message request) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		Message message = new Message();

		message.setChat(chat);
		message.setContent(request.getContent());
		message.setImage(request.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());

		Message savedMessage = messageRepository.save(message);
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		
		return savedMessage;
	}

	@Override
	public List<Message> findChatMessages(Long chatId) throws Exception {

		return messageRepository.findByChatId(chatId);
	}

}
