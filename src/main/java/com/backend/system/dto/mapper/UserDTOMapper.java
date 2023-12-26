package com.backend.system.dto.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.backend.system.dto.UserDTO;
import com.backend.system.model.User;

public class UserDTOMapper {
	public static UserDTO toUserDto(User user) {
		UserDTO userDto = new UserDTO();  
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setImage(user.getImage());
		userDto.setBackgroundImage(user.getBackgroundImage());
		userDto.setBio(user.getBio());
		userDto.setBirthDate(user.getBirthdate());
		userDto.setFollowers(toUserDtos(user.getFollowers()));
		userDto.setFollowing(toUserDtos(user.getFollowing()));
		userDto.setLogin_with_google(user.isLogin_with_google());
		userDto.setLocation(user.getLocation());
		
		
		return userDto;
	}

	public static List<UserDTO> toUserDtos(List<User> followers) {
		
		List<UserDTO> userDTOs = new ArrayList<>();
		
		for(User user : followers) {
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setFullName(user.getFullName());
			userDTO.setImage(user.getImage());
			
			userDTOs.add(userDTO);
		}
		
		return userDTOs;
	}
}
