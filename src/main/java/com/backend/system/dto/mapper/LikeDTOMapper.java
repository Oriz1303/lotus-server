package com.backend.system.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.backend.system.dto.LikeDTO;
import com.backend.system.dto.PostDTO;
import com.backend.system.dto.UserDTO;
import com.backend.system.model.Like;
import com.backend.system.model.User;

public class LikeDTOMapper {

	
	public static LikeDTO toLikeDto(Like like, User reqUser) {
		UserDTO userDto = UserDTOMapper.toUserDto(like.getUser());
		UserDTO reqUserDto = UserDTOMapper.toUserDto(reqUser);
		PostDTO postDto = PostDTOMapper.toPostDTO(like.getPost(), reqUser);
		
		LikeDTO likeDto = new LikeDTO();
		likeDto.setId(like.getId());
		likeDto.setPost(postDto);
		likeDto.setUser(userDto);
		
		return likeDto;
	}
	
	public static List<LikeDTO> toLikeDtos(List<Like> likes, User reqUser) {
		List<LikeDTO> likeDtos = new ArrayList<>();
		
		for(Like like : likes) {
			UserDTO userDto = UserDTOMapper.toUserDto(like.getUser());
			PostDTO postDto = PostDTOMapper.toPostDTO(like.getPost(), reqUser);
			
			LikeDTO likeDto = new LikeDTO();
			likeDto.setId(like.getId());
			likeDto.setPost(postDto);
			likeDto.setUser(userDto);
			
			likeDtos.add(likeDto);														
		}		
		return likeDtos;
	}
}
