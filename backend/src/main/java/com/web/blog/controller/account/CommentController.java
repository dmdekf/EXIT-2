package com.web.blog.controller.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.CommentDao;
import com.web.blog.dao.user.PimgDao;
import com.web.blog.model.user.Comment;
import com.web.blog.model.user.ImgComment;
import com.web.blog.model.user.Pimg;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("feature/comment")
public class CommentController {
	@Autowired
	CommentDao commentDao;
	@Autowired
	PimgDao pimgDao;
	
	@ApiOperation(value = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/wirte")
	public Object writeCommentWithImage(@RequestBody Comment comment) {
		System.out.println("wirte ");
		System.out.println(comment.toString());
		comment.setInsertTime(new Date());
		comment.setDeleteYn("N");
		Comment c = commentDao.save(comment);
		String uimage = pimgDao.findById(comment.getWriter()).isPresent()?pimgDao.findById(comment.getWriter()).get().getUimage():"";
		return new ImgComment(c, uimage);
	}

	@ApiOperation(value = "게시글번호에 해당하는 댓글의 정보를 반환한다.", response = CommentController.class)
	@GetMapping("/detail/{uid}/{id}/comments")
	public List<Comment> detailComment(@PathVariable String uid, @PathVariable int id) {
		List<Comment> list = commentDao.findByBoardIdx(id);
//		ArrayList<Comment> rlist = new ArrayList<>();
//		System.out.println(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getBoardIdx().equals(id)) {
//				System.out.println(list.get(i).toString());
//				rlist.add(list.get(i));
//			}
//		}
//		System.out.println(rlist.size() + " 댓글개수");
		return list;
	}

	@ApiOperation(value = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("list/detail/comments/{id}/write")
	public Comment writeComment(@RequestBody Comment comment, @PathVariable("id") int id) {
		// board = new Board(0, "test", "test", null, 0, "unknown", 300);
//    comment.setBoardIdx(id);
		System.out.println(comment.toString());
		comment.setInsertTime(new Date());
		comment.setDeleteYn("N");
		return commentDao.save(comment);
	}

	@ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 삭제한다.", response = String.class)
	@DeleteMapping("list/detail/comments/{idx}")
	public Optional<Comment> deleteComment(@PathVariable("idx") int idx) {
		commentDao.deleteById(idx);
		return null;
	}
	
	
	@ApiOperation(value = "게시글번호에 해당하는 댓글의 정보와 사진를 반환한다.", response = CommentController.class)    
	@GetMapping("/detail/{bid}")
	public Object detailCommentWithImage(@PathVariable int bid) {
		List<Comment> list = commentDao.findByBoardIdx(bid);
	    ArrayList<ImgComment> rlist = new ArrayList<>();
	    for(Comment c : list) {
	    	Optional<Pimg> p = pimgDao.findById(c.getWriter());
	    	String uimage  =p.isPresent()?p.get().getUimage():"";
	    	rlist.add(new ImgComment(c, uimage));
	    }
	    return rlist;
	}
}