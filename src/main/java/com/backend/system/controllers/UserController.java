package com.backend.system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.system.dto.UserDTO;
import com.backend.system.dto.mapper.UserDTOMapper;
import com.backend.system.exception.UserException;
import com.backend.system.model.User;
import com.backend.system.services.iservices.IUserService;
import com.backend.system.util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private IUserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
		
		User user = userService.findUserProfileByJwt(jwt);
		
		UserDTO userDto = UserDTOMapper.toUserDto(user);
		userDto.setReq_user(true);
		
		return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException { 
		
		User reqUser = userService.findUserProfileByJwt(jwt);
		
		User user = userService.findUserById(userId);

		UserDTO userDto = UserDTOMapper.toUserDto(user);
		userDto.setReq_user(UserUtil.isRequestUser(reqUser, user));
		userDto.setFollowed(UserUtil.isFollowedByRequestUser(reqUser, user));

		return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String search, @RequestHeader("Authorization") String jwt)
			throws UserException {
		
		User reqUser = userService.findUserProfileByJwt(jwt);
		
		List<User> users = userService.searchUser(search);
		
		List<UserDTO> userDtos = UserDTOMapper.toUserDtos(users);
//		userDto.setReq_user(UserUtil.isRequestUser(reqUser, user)); 
//		userDto.setFollowed(UserUtil.isFollowedByRequestUser(reqUser, user));
		
		return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt)
			throws UserException {
		
		User reqUser = userService.findUserProfileByJwt(jwt);
		
		User user = userService.updateUser(reqUser.getId(), req);
		
		UserDTO userDto = UserDTOMapper.toUserDto(user);
		
		return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{userId}/follow")
	public ResponseEntity<UserDTO> followedUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException { 
		
		User reqUser = userService.findUserProfileByJwt(jwt);
		
		User user = userService.followUser(userId, reqUser);
		
		UserDTO userDto = UserDTOMapper.toUserDto(user);
		userDto.setFollowed(UserUtil.isFollowedByRequestUser(reqUser, user));
		
		return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
	}
}
