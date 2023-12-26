package com.backend.system.dto;

import lombok.Data;

@Data
public class LikeDTO {
	private Long id;
	private UserDTO user;
	private PostDTO post;
	
}
