package com.backend.system.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.backend.system.dto.PostDTO;
import com.backend.system.dto.UserDTO;
import com.backend.system.model.Post;
import com.backend.system.model.User;
import com.backend.system.util.PostUtil;

public class PostDTOMapper {
	public static PostDTO toPostDTO(Post post, User reqUser) {

		UserDTO userDTO = UserDTOMapper.toUserDto(post.getUser());

		boolean isLiked = PostUtil.isLikedByRequestUser(reqUser, post);
		boolean isShared = PostUtil.isSharedByRequestUser(reqUser, post);

		List<Long> sharedUserId = new ArrayList<>();
		for (User user : post.getSharedUser()) {
			sharedUserId.add(user.getId());
		} 
		
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setContent(post.getContent());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setImage(post.getImage());
		postDTO.setTotalLikes(post.getLikes().size());
		postDTO.setTotalComments(post.getCommentsPost().size());
		postDTO.setTotalSharedPost(post.getSharedUser().size());
		postDTO.setUser(userDTO);
		postDTO.setLiked(isLiked);
		postDTO.setShared(isShared);
		postDTO.setSharedUserId(sharedUserId);
		postDTO.setCommentsPost(toPostDTOs(post.getCommentsPost(), reqUser));

		postDTO.setVideo(post.getVideo());

		return postDTO;

	}

	public static List<PostDTO> toPostDTOs(List<Post> posts, User reqUser) {
		List<PostDTO> postDtos = new ArrayList<>();
		for (Post post : posts) {
			PostDTO postDto = toCommentPostDto(post, reqUser);
			postDtos.add(postDto);
		}

		return postDtos;
	}

	private static PostDTO toCommentPostDto(Post post, User reqUser) {
		UserDTO userDTO = UserDTOMapper.toUserDto(post.getUser());

		boolean isLiked = PostUtil.isLikedByRequestUser(reqUser, post);
		boolean isShared = PostUtil.isSharedByRequestUser(reqUser, post);

		List<Long> sharedUserId = new ArrayList<>();
		for (User user : post.getSharedUser()) {
			sharedUserId.add(user.getId());
		}

		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setContent(post.getContent());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setImage(post.getImage());
		postDTO.setTotalLikes(post.getLikes().size());
		postDTO.setTotalComments(post.getCommentsPost().size());
		postDTO.setTotalSharedPost(post.getSharedUser().size());
		postDTO.setUser(userDTO);
		postDTO.setLiked(isLiked);
		postDTO.setShared(isShared);
		postDTO.setSharedUserId(sharedUserId);
		postDTO.setVideo(post.getVideo());
//		postDTO.setCommentsPost(toPostDTOs(post.getCommentsPost(), reqUser));

		return postDTO;
	}
}
