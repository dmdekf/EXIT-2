package com.web.blog.controller.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.BoardDao;
import com.web.blog.dao.user.TagDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Board;
import com.web.blog.model.user.Heart;
import com.web.blog.model.user.Taglist;
import com.web.blog.model.user.TagPK;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
public class TagController {

	@Autowired
	TagDao tagDao;
	@Autowired
	BoardDao boardDao;

	@ApiOperation(value = "태그 입력 반환한다.", response = List.class)
	@GetMapping("/tag/{bid}/{tag}")
	public Object like(@PathVariable int bid, @PathVariable String tag) throws Exception {

		Optional<Board> board = boardDao.findById(bid);
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if (board.isPresent()) {
			if (!tagDao.findById(new TagPK(bid, tag)).isPresent()) {
				tagDao.save(new Taglist(bid, tag));
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.status = false;
				result.data = "중복된 태그";
				response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
		} else {
			result.status = false;
			result.data = "null Post";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiOperation(value = "tag 지우기", response = List.class)
	@DeleteMapping("/tag/{bid}/{tag}")
	public Object countLike(@PathVariable int bid, @PathVariable String tag) throws Exception {
		Optional<Taglist> t = tagDao.findById(new TagPK(bid, tag));
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if(!t.isPresent()) {
			
			tagDao.delete(new Taglist());
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			result.status = false;
			result.data = "not exist tag";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

}
