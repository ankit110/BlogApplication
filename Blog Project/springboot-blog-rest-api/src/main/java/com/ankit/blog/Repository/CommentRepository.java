package com.ankit.blog.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.blog.Entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> getCommentByPostId(long postId);
}
