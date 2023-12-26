package com.backend.system.services.iservices;

import java.util.List;

import com.backend.system.model.Message;
import com.backend.system.model.User;

public interface IMessageService {
	public Message createMessage(User user, Long chatId, Message request) throws Exception;
	public List<Message> findChatMessages(Long chatId) throws Exception;
}
