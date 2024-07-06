package com.ankit.blog.Payload;

import java.util.Set;

import lombok.Data;

@Data
public class PostDTO {
	private Long id;
	private String title;
	private String description;
	private String content;
	
	private Set<CommentDto> comments;
}
