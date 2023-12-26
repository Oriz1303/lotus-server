package com.backend.system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Like;
import com.backend.system.model.Post;
import com.backend.system.model.User;
import com.backend.system.repositories.LikeRepository;
import com.backend.system.repositories.PostRepository;
import com.backend.system.services.iservices.ILikeService;
import com.backend.system.services.iservices.IPostService;

@Service
public class LikeService implements ILikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private IPostService postService;

	@Override
	public Like likePost(Long postId, User user) throws UserException, PostException {
		Like isLikeExist = likeRepository.isLikeExist(user.getId(), postId);
		
		if(isLikeExist != null) {
			likeRepository.deleteById(isLikeExist.getId());
			return isLikeExist;
		}
		
		Post post = postService.findById(postId);
		
		Like like = new Like();
		like.setPost(post);
		like.setUser(user);
		
		Like savedLike = likeRepository.save(like);
		post.getLikes().add(savedLike);
		postRepository.save(post);
		return savedLike;
	}

	@Override
	public List<Like> getAllLikes(Long postId) throws PostException {
		Post post = postService.findById(postId);
		
		List<Like> likes = likeRepository.findByPostId(postId);
		
		return likes;
	}

}
