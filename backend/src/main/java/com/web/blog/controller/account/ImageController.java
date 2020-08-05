package com.web.blog.controller.account;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Profile;

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
	UserDao userDao;
	
	@PostMapping("/user/image")
	@ApiOperation(value = "회원사진")
	public Object image(@RequestParam("profile") MultipartFile profile, @RequestParam("uid") String uid) throws Exception {
		System.out.println("이거시 실행");
		System.out.println(profile);
		System.out.println(uid);
		System.out.println(profile.getName());
		System.out.println(profile.getContentType());
		System.out.println(profile.getOriginalFilename());
		System.out.println(profile.getSize());
		System.out.println(profile.getBytes());

		
		try {
			profile.transferTo(new File("c:/img/"+profile.getOriginalFilename()));
		}catch(Exception e) {
			System.out.println("에러");
		}
		ResponseEntity response = null;
		return response;
	}
}