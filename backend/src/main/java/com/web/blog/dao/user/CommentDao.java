package com.web.blog.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Comment;



public interface CommentDao extends JpaRepository<Comment, Integer>{

}
