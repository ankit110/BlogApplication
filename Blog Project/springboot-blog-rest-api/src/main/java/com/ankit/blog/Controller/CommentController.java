package com.ankit.blog.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.blog.Payload.CommentDto;
import com.ankit.blog.Service.CommentService;

@RestController
@RequestMapping("/api/posts/")
public class CommentController {
	CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
//-----------------------------------------------------------------------------------
	
	// Create Rest Api for Comment for given postId
	
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
//-------------------------------------------------------------------------------------
	
	// Create Rest API for Get all Comments for given postId.
	
	@GetMapping("/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(value = "postId") long postId) {
		return ResponseEntity.ok(commentService.getAllComments(postId));
	}
//------------------------------------------------------------------------------------------------
	
	// Create rest API for get comment by id respective to the given postId
	
	@GetMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId) {
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}
//---------------------------------------------------------------------------------------------------
	
	// create rest API to update comment by id respective to the given postId.
	
	@PutMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") Long postId,
														@PathVariable(value = "commentId") Long commentId, 
														@RequestBody CommentDto commentDto) {
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
//----------------------------------------------------------------------------------------------------
	
	// Create Rest API to delete comment by id respective to the given postId.
	
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") Long postId,
													@PathVariable(value = "commentId") Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
	}
}
