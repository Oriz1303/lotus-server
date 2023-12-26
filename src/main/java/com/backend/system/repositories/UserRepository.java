package com.backend.system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.system.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%'))")
	public List<User> searchUser(@Param("query") String query);
}
