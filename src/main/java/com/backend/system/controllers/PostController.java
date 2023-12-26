package com.backend.system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.system.dto.PostDTO;
import com.backend.system.dto.mapper.PostDTOMapper;
import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Post;
import com.backend.system.model.User;
import com.backend.system.repositories.UserRepository;
import com.backend.system.requests.PostCommentRequest;
import com.backend.system.response.ApiResponse;
import com.backend.system.services.iservices.IPostService;
import com.backend.system.services.iservices.IUserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserService userService;

	@PostMapping("/create")
	public ResponseEntity<PostDTO> createPost(@RequestBody Post req, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);

		Post post = postService.createPost(req, user);

		PostDTO postDto = PostDTOMapper.toPostDTO(post, user);

		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}

	@GetMapping("/test")
	public ResponseEntity<List<Post>> commentPostTest(@RequestHeader("Authorization") String jwt)
			throws UserException, PostException {

//		User user = userService.findUserProfileByJwt(jwt);

		User user = userService.findUserProfileByJwt(jwt);
		List<Post> posts = postService.findAllPost();

//		PostDTO postDto = PostDTOMapper.toPostDTO(post, user);

		return new ResponseEntity<>(posts, HttpStatus.CREATED);
	}

	@PostMapping("/comment")
	public ResponseEntity<PostDTO> commentPost(@RequestBody PostCommentRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, PostException {

		User user = userService.findUserProfileByJwt(jwt);

		Post post = postService.createdComment(req, user);

		PostDTO postDto = PostDTOMapper.toPostDTO(post, user);

		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}

	@PutMapping("/{postId}/shared")
	public ResponseEntity<PostDTO> sharedPost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		Post post = postService.shared(postId, user);
		PostDTO postDto = PostDTOMapper.toPostDTO(post, user);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}
	

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
//		User user = userService.findUserProfileByJwt(jwt);
		User user = userService.findUserById(userId);
		List<Post> post = postService.getUserPost(user);
		List<PostDTO> postDtos = PostDTOMapper.toPostDTOs(post, user);
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDTO> findPostById(@PathVariable Long postId, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		Post post = postService.findById(postId);
		PostDTO postDto = PostDTOMapper.toPostDTO(post, user);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		postService.deletePostById(postId, user.getId());
		ApiResponse res = new ApiResponse();
		res.setMessage("Post deleted!");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<PostDTO>> getAllPosts(@RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Post> posts = postService.findAllPost();
		List<PostDTO> postDtos = PostDTOMapper.toPostDTOs(posts, user);
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}
//
//	@GetMapping("/user")
//	public ResponseEntity<List<PostDTO>> getUserAllPosts(@RequestHeader("Authorization") String jwt)
//			throws UserException, PostException {
//		User user = userService.findUserProfileByJwt(jwt);
//		List<Post> posts = postService.getUserPost(user);
//		List<PostDTO> postDtos = PostDTOMapper.toPostDTOs(posts, user);
//		return new ResponseEntity<>(postDtos, HttpStatus.OK);
//	}

	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<PostDTO>> findPostByLikeContaines(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Post> posts = postService.findByLikesContainsUser(user);
		List<PostDTO> postDtos = PostDTOMapper.toPostDTOs(posts, user);
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

}
