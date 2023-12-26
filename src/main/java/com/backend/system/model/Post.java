package com.backend.system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@ManyToOne
	private User user;
	 
	private String content;
	private String image;
	private String video;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "commentFor", cascade = CascadeType.ALL)
	private List<Post> commentsPost = new ArrayList<>();
	
	@ManyToMany
	private List<User> sharedUser = new ArrayList<>();
	
	@ManyToOne
	private Post commentFor;
	
	private boolean isComment;
	private boolean isPost;
	
	private LocalDateTime createdAt;
}
