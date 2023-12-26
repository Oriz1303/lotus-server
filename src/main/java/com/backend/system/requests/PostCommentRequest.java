package com.backend.system.requests;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class PostCommentRequest {
	private String content;
	private Long postId;
	private  LocalDateTime createdAt;
	private String image;
}
