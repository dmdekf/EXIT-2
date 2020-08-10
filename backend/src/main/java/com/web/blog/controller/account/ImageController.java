package com.web.blog.controller.account;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.dao.user.PimgDao;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Pimg;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//port 2개를 열어놓을때 ... Vue를 번으로 열어줘야한다
@CrossOrigin(origins = { "*" })
@RestController
public class ImageController {
	
	@Autowired
	PimgDao pimgDao ; 
	@Autowired
	UserDao userDao;
	
	@PostMapping("/user/image")
	@ApiOperation(value = "회원사진")
	public Object image(@RequestParam("profile") MultipartFile file, @RequestParam("uid") String uid) throws Exception {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();System.out.println("이거시 실행");
		System.out.println(file);
		System.out.println(uid);
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		if(!userDao.findById(uid).isPresent()) {
			result.status = false;
			result.data = "not find user";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
			return response;
		}
		try {
			file.transferTo(new File("c:/img/"+uid+file.getOriginalFilename()));
			pimgDao.save(new Pimg(uid,"c:/img"+uid+file.getOriginalFilename()));
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e) {
			System.out.println("에러");
			result.status = false;
			result.data = "fail";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	@PostMapping("/getImg/{uid}")
	@ApiOperation(value = "회원사진 가져오기")
	public Object getImage(@PathVariable String uid) throws Exception {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		System.out.println("회원사진가져오기");
		Optional<Pimg> pimg = pimgDao.findById(uid);
		if(pimg.isPresent()) {
			result.status = true;
			result.data = "success";
			result.object = pimg.get();
			response = new ResponseEntity<>(result,HttpStatus.OK);
			return response;
		}else {
			result.status = false;
			result.data = "can't find image";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	

	
}