package com.backend.system.services.iservices;

import java.util.List;

import com.backend.system.model.Chat;
import com.backend.system.model.User;

public interface IChatService {
	public Chat createChat(User requestUser, User partner);
	
	public Chat findChatById(Long chatId) throws Exception;
	
	public List<Chat> findUsersChat(Long userId);
}
