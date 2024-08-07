package com.ankit.blog.Controller;

import java.util.List;
import com.ankit.blog.utils.AppConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.blog.Payload.PostDTO;
import com.ankit.blog.Payload.PostResponse;
import com.ankit.blog.Service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	PostService postService; // Here we r doing loose coupling(instead of injecting class we are injecting interface here)
							 // if we inject class directly it will become tight coupling.

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	// create rest api method for blog post.
//----------------------------------------------------------------------------------------
	
	// PostMapping 👇👇
	
	// Rest API for create Post
	
	@PostMapping("/createPost")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
//------------------------------------------------------------------------------------------

	// GetMapping 👇👇
	
	// RestApi method for GET all posts
	
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			) {
		return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
	}
	
	// RestApi method for get post by Id.
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
//-------------------------------------------------------------------------------------------
	
	// PutMapping 👇👇
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDto, @PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.updatePostById(postDto, id));
	}
//--------------------------------------------------------------------------------------------
	
	// DeleteMapping 👇👇
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
		postService.deleteByid(id);
		return ResponseEntity.ok("Post Deleted Succesfully");
	}
}
