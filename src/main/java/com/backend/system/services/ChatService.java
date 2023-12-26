package com.backend.system.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.system.model.Chat;
import com.backend.system.model.User;
import com.backend.system.repositories.ChatRepository;
import com.backend.system.services.iservices.IChatService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService implements IChatService {
	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Chat createChat(User requestUser, User partner) {
		Chat isExist = chatRepository.findChatByUsersId(requestUser, partner);
		if (isExist != null) {
			return isExist;
		}

		Chat chat = new Chat();
		chat.getUsers().add(partner);
		chat.getUsers().add(requestUser);
		chat.setTimestamp(LocalDateTime.now());

		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Long chatId) throws Exception {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isEmpty()) {
			throw new Exception("Chat not found with id: " + chatId);
		}

		return opt.get();
	}

	@Override
	public List<Chat> findUsersChat(Long userId) {
		return chatRepository.findByUsersId(userId);
	}

}
