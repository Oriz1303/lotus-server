package com.backend.system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.system.model.Post;
import com.backend.system.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByIsPostTrueOrderByCreatedAtDesc();
	List<Post> findBySharedUserContainsOrUser_IdAndIsPostTrueOrderByCreatedAtDesc(User user, Long userId);
	List<Post> findByLikesContainingOrderByCreatedAtDesc(User user);
	
	@Query("SELECT p FROM Post p JOIN p.likes l WHERE l.user.id = :userId")
	List<Post> findByLikesUser_Id(@Param("userId")Long userId);
	
}
