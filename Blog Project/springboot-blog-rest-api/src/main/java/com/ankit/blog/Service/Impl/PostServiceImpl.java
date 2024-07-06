package com.ankit.blog.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ankit.blog.CustomException.ResourceNotFoundException;
import com.ankit.blog.Entity.Post;
import com.ankit.blog.Payload.PostDTO;
import com.ankit.blog.Payload.PostResponse;
import com.ankit.blog.Repository.PostRepository;
import com.ankit.blog.Service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepo;
	private ModelMapper modelMapper;
	
	// here we are going to use constructor based dependency injection.	
//	@Autowired
	public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
		super();
		this.postRepo = postRepo;
		this.modelMapper = modelMapper;
	}
//----------------------------------------------------------------------	
	// Create Post.
	@Override
	public PostDTO createPost(PostDTO postDto) {
		// TODO Auto-generated method stub
		
		// convert DTO to entity
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		
		Post post = mapToEntity(postDto);
		
		Post newPost = postRepo.save(post);
		
		// convert Entity to DTO
		PostDTO postResponse = mapToDto(newPost);
		
		return postResponse;
	}
//---------------------------------------------------------
	
	// Get All Post.
	
	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
		// create the sort object if these are in ascending or descending order 
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		// Create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort); 
		// By default Sort.by will sort it in ascending order 
		// If we want to sort in descending order then modify the code little bit "Sort.by(sortBy).descending()"
		Page<Post> posts = postRepo.findAll(pageable);
		
		// get content from page object in list form.
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
//------------------------------------------------------------------
	
	//Get Post By Id
	@Override
	public PostDTO getPostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}
//------------------------------------------------------------------
	
	// Update Post By Id
	
	@Override
	public PostDTO updatePostById(PostDTO postDto, long id) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepo.save(post);
		return mapToDto(updatedPost);
	}
	
//---------------------------------------------------------------------
	
	// Delete Post By Id
	
	@Override
	public void deleteByid(long id) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepo.delete(post);
	}
//-----------------------------------------------------------------------
	
	// Convert Entity to DTO.
	
	private PostDTO mapToDto(Post post) {
		PostDTO postDto = modelMapper.map(post, PostDTO.class);
		
//		PostDTO postDto = new PostDTO();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
//		
		return postDto;
	}
//---------------------------------------------------------------------
	
	// Convert DTO to Entity
	private Post mapToEntity(PostDTO postDto) {
		Post post = modelMapper.map(postDto, Post.class); // It reduces the boilerplate code.
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		
		return post;
	}
	

	

}
