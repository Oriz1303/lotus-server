package com.backend.system.services.iservices;

import java.util.List;

import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Like;
import com.backend.system.model.User;

public interface ILikeService {
	public Like likePost(Long postId, User user) throws UserException, PostException;
	
	public List<Like> getAllLikes(Long postId) throws PostException;
	
	
}
