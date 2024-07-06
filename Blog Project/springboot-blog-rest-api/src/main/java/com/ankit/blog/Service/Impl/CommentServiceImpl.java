package com.ankit.blog.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ankit.blog.CustomException.BlogAPIException;
import com.ankit.blog.CustomException.ResourceNotFoundException;
import com.ankit.blog.Entity.Comment;
import com.ankit.blog.Entity.Post;
import com.ankit.blog.Payload.CommentDto;
import com.ankit.blog.Repository.CommentRepository;
import com.ankit.blog.Repository.PostRepository;
import com.ankit.blog.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	private CommentRepository commentRepo;
	private PostRepository postRepo;
	private ModelMapper modelMapper;
	
	// Constructor Based Dependency
	public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
		this.commentRepo = commentRepo;
		this.postRepo = postRepo;
		this.modelMapper = modelMapper;
	}
//-------------------------------------------------------------------------------------------

	// Create Comment
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		// TODO Auto-generated method stub
		Comment comment = mapToEntity(commentDto);
		
		// retrieve post entity by Id
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// Set Post to comment Entity
		comment.setPost(post);
		
		// Comment Entity to DataBase.
		Comment newComment = commentRepo.save(comment);
		
		return mapToDto(newComment);
	}
//--------------------------------------------------------------------------------------------------------------------
	
	// Get All Comments
	
	@Override
	public List<CommentDto> getAllComments(long postId) {
		// TODO Auto-generated method stub
		List<Comment> comments = commentRepo.getCommentByPostId(postId);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
//-------------------------------------------------------------------------------------------
	
	// Get Comment by id
	
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to respective post.");
		}
		return mapToDto(comment);
	}
//------------------------------------------------------------------------------------
	
	// Update comment by id respective to the post.
	
	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId).orElseThrow(() -> 
			new ResourceNotFoundException("Post", "id", postId)
				);
		
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> 
			new ResourceNotFoundException("Comment", "id", commentId)
				);
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to respective post.");
		}
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment updatedComment = commentRepo.save(comment);
				
		return mapToDto(updatedComment);
	}
	
//------------------------------------------------------------------------------------
	
	// Delete comment by id respective to the given Id.
	
	@Override
	public void deleteComment(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId).orElseThrow(()->
												new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->
												new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to respective post.");
		}
		
		commentRepo.delete(comment);
		
	}
//---------------------------------------------------------------------------------------
	
	// Convert Entity to CommentDto
	
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		
		return commentDto;
	}
//----------------------------------------------------------------------------------------------	
	
	// Convert CommentDto to Entity
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		
		return comment;
	}
//--------------------------------------------------------------------------------------------------
	
	// Get Comment and Post with their respective id's.
	
	
}
