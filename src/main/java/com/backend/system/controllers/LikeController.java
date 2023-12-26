package com.backend.system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.system.dto.LikeDTO;
import com.backend.system.dto.mapper.LikeDTOMapper;
import com.backend.system.exception.PostException;
import com.backend.system.exception.UserException;
import com.backend.system.model.Like;
import com.backend.system.model.User;
import com.backend.system.services.iservices.ILikeService;
import com.backend.system.services.iservices.IPostService;
import com.backend.system.services.iservices.IUserService;

@RestController
@RequestMapping("/api")
public class LikeController {
	@Autowired
	private IUserService userService;
	@Autowired
	private ILikeService likeService;

	@PostMapping("/{postId}/likes")
	public ResponseEntity<LikeDTO> likePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt)
			throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		Like like = likeService.likePost(postId, user);

		LikeDTO likeDto = LikeDTOMapper.toLikeDto(like, user);

		return new ResponseEntity<LikeDTO>(likeDto, HttpStatus.CREATED);
	}

	@PostMapping("/{postId}/post")
	public ResponseEntity<List<LikeDTO>> getAllLikes(@PathVariable Long postId,
			@RequestHeader("Authorization") String jwt) throws UserException, PostException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Like> likes = likeService.getAllLikes(postId);

		List<LikeDTO> likeDtos = LikeDTOMapper.toLikeDtos(likes, user);

		return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
	}
}
