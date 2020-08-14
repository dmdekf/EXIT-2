package com.web.blog.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Comment;



public interface CommentDao extends JpaRepository<Comment, Integer>{
	//List<Comment> findByBoardIdx(String bid);
	List<Comment> findByBoardIdx(int bid);
} 
  