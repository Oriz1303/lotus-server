package com.backend.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
	
	private Long id;
	
	private String content;
	
	private String image;
	
	private String video;
	
	private UserDTO user;
	
	private LocalDateTime createdAt;
	
	private int totalLikes;
	
	private int totalComments;
	
	private int totalSharedPost;
	
	private boolean isLiked;
	
	private boolean isShared;
	
	private List<Long> sharedUserId;
	
	private List<PostDTO> commentsPost;
}
