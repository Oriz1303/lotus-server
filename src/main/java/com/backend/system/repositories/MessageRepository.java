package com.backend.system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.system.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	public List<Message> findByChatId(Long chatId);
	
	
}
