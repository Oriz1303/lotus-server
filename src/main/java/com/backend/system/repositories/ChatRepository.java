package com.backend.system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.system.model.Chat;
import com.backend.system.model.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	public List<Chat> findByUsersId(Long userId);
	
	@Query("SELECT c FROM Chat c WHERE :requestUser MEMBER OF c.users AND :partner MEMBER OF c.users ")
	public Chat findChatByUsersId(@Param("requestUser") User user, @Param("partner") User partner);
}
