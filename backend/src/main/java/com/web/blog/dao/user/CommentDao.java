package com.web.blog.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Comment;



public interface CommentDao extends JpaRepository<Comment, Integer>{
<<<<<<< HEAD
	
=======
	List<Comment> findByBoardIdx(String bid);
>>>>>>> 48089e1e54c8267b84c904e135c9793b5b55d85e
}
