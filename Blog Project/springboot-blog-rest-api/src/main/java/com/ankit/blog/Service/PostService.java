package com.ankit.blog.Service;

import java.util.List;

import com.ankit.blog.Payload.PostDTO;
import com.ankit.blog.Payload.PostResponse;

public interface PostService {
	PostDTO createPost(PostDTO postDto);
	PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
	PostDTO getPostById(long id);
	PostDTO updatePostById(PostDTO postDto, long id);
	void deleteByid(long id);
}
