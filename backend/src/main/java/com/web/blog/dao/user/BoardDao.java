package com.web.blog.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Board;

public interface BoardDao extends JpaRepository<Board, Integer> {
	//List<Board> findBoardBySubject(String title);
	List<Board> findBoardBySubjectLike(String title);
	List<Board> findBoardByContentLike(String content);
	List<Board> findBoardByUidLike(String uid);
	List<Board> findBoardByTag(String tag);
	Optional<Board> findById(int id);
	Optional<Board> deleteById(int id);
}

