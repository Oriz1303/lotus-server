package com.backend.system.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Post;
import com.backend.system.model.User;
import com.backend.system.repositories.PostRepository;
import com.backend.system.requests.PostCommentRequest;
import com.backend.system.services.iservices.IPostService;

@Service
public class PostService implements IPostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public Post createPost(Post req, User user) throws UserException {
		Post post = new Post();
		post.setContent(req.getContent());
		post.setCreatedAt(LocalDateTime.now());
		post.setImage(req.getImage());
		post.setUser(user);
		post.setComment(false);
		post.setPost(true);
		post.setVideo(req.getVideo());

		return postRepository.save(post);
	}

	@Override
	public List<Post> findAllPost() {
		return postRepository.findAllByIsPostTrueOrderByCreatedAtDesc();
	}

	@Override
	public Post shared(Long postId, User user) throws UserException, PostException {
		Post post = findById(postId);
		if (post.getSharedUser().contains(user)) {
			post.getSharedUser().remove(user);
		} else {
			post.getSharedUser().add(user);
		}

		return postRepository.save(post);
	}

	@Override
	public Post findById(Long postId) throws PostException {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostException("Post not found with id " + postId + "! PostSer 57"));
		return post;
	}

	@Override
	public void deletePostById(Long postId, Long userId) throws UserException, PostException {
		Post post = findById(postId);
		if (!userId.equals(post.getUser().getId())) {
			throw new UserException("U cant delete another  users post! PostSer 65");
		}

		postRepository.deleteById(post.getId());
	}

	@Override
	public Post removeFromShared(Long postId, Long userId) throws UserException, PostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post createdComment(PostCommentRequest req, User user) throws PostException {

		Post commentFor = findById(req.getPostId());

		Post post = new Post();
		post.setContent(req.getContent());
		post.setCreatedAt(LocalDateTime.now());
		post.setImage(req.getImage());
		post.setUser(user);
		post.setComment(true);
		post.setPost(false);
		post.setCommentFor(commentFor);

		Post savedComment = postRepository.save(post);
		post.getCommentsPost().add(savedComment);
	
		postRepository.save(commentFor);

		return commentFor;
	}

	@Override
	public List<Post> getUserPost(User user) {
		return postRepository.findBySharedUserContainsOrUser_IdAndIsPostTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Post> findByLikesContainsUser(User user) {
		return postRepository.findByLikesUser_Id(user.getId());
	}

}
