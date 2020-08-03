package com.web.blog.controller.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.HeartDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Board;
import com.web.blog.model.user.Heart;
import com.web.blog.model.user.HeartPK;
import com.web.blog.model.user.Post;

import io.swagger.annotations.ApiOperation;
@RestController
@CrossOrigin(origins = { "*" })
public class HeartController {

	@Autowired
	HeartDao heartDao;

	
	@ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/like/{bid}/{uid}")
	public Object like(@PathVariable String bid, @PathVariable String uid) throws Exception {
		Optional<Heart> heart = heartDao.findHeartByBidAndUid(bid, uid);
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if (heart.isPresent()) {
			heartDao.deleteById(new HeartPK(bid, uid));
			result.status = true;
			result.data = "";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}
		heartDao.save(new Heart(bid, uid));
		result.status = true;
		result.data = "1";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "하트의 개수를 반환", response = List.class)
   @GetMapping("/heart/{bid}")
   public int countLike(@PathVariable String bid) throws Exception {
      List<Heart> heart = heartDao.findHeartByBid(bid);
       return heart.size();
   }

}
