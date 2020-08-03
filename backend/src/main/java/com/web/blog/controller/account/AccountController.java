package com.web.blog.controller.account;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.service.MailSendService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//port 2개를 열어놓을때 ... Vue를 3000번으로 열어줘야한다
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

	@Autowired
	UserDao userDao;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MailSendService mailSendService;

	@GetMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password,HttpServletResponse res) {

		Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if (userOpt.isPresent()) {
			User loginUser = userOpt.get();

			String token = jwtService.create(loginUser);
			
			res.setHeader("jwt-auth-token", token);

			result.status = true;
			result.data = "success";
			result.object = loginUser;
			
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}

	
	@PostMapping("/account/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@Valid @RequestBody SignupRequest request) {
		ResponseEntity response = null;
		// 이메일, 닉네임 중복처리 필수
		Optional<User> userNickname = userDao.findUserByUid(request.getNickname());
		Optional<User> userEmail = userDao.findUserByEmail(request.getEmail());
		final BasicResponse result = new BasicResponse();

		if(userNickname.isPresent()) {//닉네임(별명) 중복시
			result.status = false;
			result.data = "nickname";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}else if(userEmail.isPresent()) {//이메일 중복시
			result.status = false;
			result.data = "email";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}else {// 이메일 혹은 닉네임이 중복되지 않으면 회원가입 후 이메일 발송
			LocalDateTime currentTime = LocalDateTime.now();
			User u = new User(request.getNickname(), request.getPassword(), request.getEmail(),currentTime,null);
			u = userDao.save(u);

			String key = mailSendService.getKey(false,20);
			u.setUserkey(key);
			userDao.save(u);
			mailSendService.mailSendWithUserKey(u.getEmail(), u.getUid(), key);


			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result,HttpStatus.OK);
		}

		return response;
	}

	@GetMapping("/account/key_alter")
	@ApiOperation(value="이메일 인증 완료")
	public Object keyAlterConfirm(@RequestParam String uid,@RequestParam String userkey) {
		Optional<User> userOpt = userDao.findUserByUidAndUserkey(uid, userkey);

		ResponseEntity response = null;
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setUserkey("Y");
			userDao.save(user);
			final BasicResponse result = new BasicResponse(); 
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}
}