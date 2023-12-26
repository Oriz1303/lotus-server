package com.backend.system.util;

import com.backend.system.model.User;

public class UserUtil {
	public static final boolean isRequestUser(User reqUser, User user) {
		return reqUser.getId().equals(user.getId());
	}
	
	public static final boolean isFollowedByRequestUser(User reqUser, User user) {
		return reqUser.getFollowing().contains(user);
	}
}
