package com.backend.system.services.iservices;

import java.util.List;

import com.backend.system.exception.UserException;
import com.backend.system.model.User;

public interface IUserService {
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User updateUser(Long userId, User reqUser)throws UserException;
	
	public User followUser(Long userId, User reqUser) throws UserException;
	
	public List<User> searchUser(String query);
}
