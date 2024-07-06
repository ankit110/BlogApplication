package com.ankit.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.blog.Entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

}
