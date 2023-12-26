package com.backend.system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.system.config.JwtProvider;
import com.backend.system.exception.UserException;
import com.backend.system.model.User;
import com.backend.system.repositories.UserRepository;
import com.backend.system.services.iservices.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserById(Long userId) throws UserException {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Cant found user with id " + userId));
		return user;
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("user not found with email " + email);
		}
		return user;
	}

	@Override
	public User updateUser(Long userId, User reqUser) throws UserException {
		User user = findUserById(userId);
		
		if(reqUser.getFullName() != null) {
			user.setFullName(reqUser.getFullName());
		}
		
		if(reqUser.getImage() != null) {
			user.setImage(reqUser.getImage());
		}
		
		if(reqUser.getBackgroundImage() != null) {
			user.setBackgroundImage(reqUser.getBackgroundImage());
		}
		
		if(reqUser.getBirthdate() != null) {
			user.setBirthdate(reqUser.getBirthdate());
		}
		
		if(reqUser.getLocation() != null) {
			user.setLocation(reqUser.getLocation());
		}
		
		if(reqUser.getBio() != null) {
			user.setBio(reqUser.getBio());
		}
		
		if(reqUser.getWebsite() != null) {
			user.setWebsite(reqUser.getWebsite());
		}
		
		return userRepository.save(user);
	}

	@Override
	public User followUser(Long userId, User reqUser) throws UserException {
		User followToUser =  findUserById(userId);
		
		if(reqUser.getFollowing().contains(followToUser) && followToUser.getFollowers().contains(reqUser)) {
			 reqUser.getFollowing().remove(followToUser);
			 followToUser.getFollowers().remove(reqUser);
		} else {
			reqUser.getFollowing().add(followToUser);
			followToUser.getFollowers().add(reqUser);
		}
		
		userRepository.save(followToUser);
		userRepository.save(reqUser);
		return followToUser;
	}

	@Override
	public List<User> searchUser(String query) {
		
		return userRepository.searchUser(query);
	}

}
