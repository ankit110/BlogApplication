package com.ankit.blog.Service;

import java.util.List;

import com.ankit.blog.Payload.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);
	List<CommentDto> getAllComments(long postId);
	CommentDto getCommentById(long postId, long commentId);
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
	void deleteComment(Long postId, Long commentId);
}
