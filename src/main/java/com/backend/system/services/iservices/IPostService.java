package com.backend.system.services.iservices;

import java.util.List;

import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Post;
import com.backend.system.model.User;
import com.backend.system.requests.PostCommentRequest;

public interface IPostService {
	public Post createPost (Post req, User user) throws UserException;
	
	public List<Post> findAllPost();
	
	public Post shared(Long postId, User user) throws UserException, PostException;
	
	public Post findById(Long postId) throws PostException;
	
	public void deletePostById(Long postId, Long userId) throws UserException, PostException;
	
	public Post removeFromShared(Long postId, Long userId) throws UserException, PostException;
	
	public Post createdComment(PostCommentRequest req, User user) throws PostException;
	
	public List<Post> getUserPost(User user);
	
	public List<Post> findByLikesContainsUser(User user);
	
}
