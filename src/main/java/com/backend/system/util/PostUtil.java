package com.backend.system.util;

import com.backend.system.model.Like;
import com.backend.system.model.Post;
import com.backend.system.model.User;

public class PostUtil {
	public final static boolean isLikedByRequestUser(User reqUser, Post post) {
		for (Like like : post.getLikes()) {
			if(like.getUser().getId().equals(reqUser.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public final static boolean isSharedByRequestUser(User reqUser, Post post) {
		for(User user: post.getSharedUser())  {
			if(user.getId().equals(reqUser.getId())) {
				return true;
			}
		}
		
		return false;
	}
}
