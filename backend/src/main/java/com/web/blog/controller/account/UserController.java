package com.web.blog.controller.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;

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
public class UserController {

	@Autowired
	UserDao userDao;
	
	@GetMapping("/user/detail/{uid}")
    @ApiOperation(value = "회원정보조회")
    public Object search(@PathVariable String uid) throws Exception {
        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        Optional<User> user = userDao.findUserByUid(uid);
        if(user.isPresent()) {
        	result.status = true;
        	result.data = "success";
        	result.object = user.get();
        	System.out.println(user.get());
        	response = new ResponseEntity<>(result, HttpStatus.OK);
        	
        }else {
        	result.status = false;
			result.data = "find Error";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }

        return response;
    }
	@PutMapping("/user/update")
    @ApiOperation(value = "회원정보수정")
    public Object update(@RequestBody User request) throws Exception {
        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
       
        Optional<User> user = userDao.findUserByUid(request.getUid());
        
        if(user.isPresent()) {
        	System.out.println("수정");
        	System.out.println(request.getPassword());
        	User u = user.get();
        	u.setEmail(request.getEmail());
        	if(request.getPassword()!=null) {
        		u.setPassword(request.getPassword());
        	}
        	u = userDao.save(u);
        	// Password는 별도 
        	result.status = true;
        	result.data = "success";
        	result.object = u;
        	System.out.println(user.get());
        	response = new ResponseEntity<>(result, HttpStatus.OK);
        	
        }else {
        	result.status = false;
			result.data = "find Error";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }

        return response;
    }
    @DeleteMapping("/user/delete/{uid}")
    @ApiOperation(value = "회원 탈퇴")
    public Object delete(@PathVariable String uid) {
        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        System.out.println(uid);
        System.out.println("탈퇴하기");
        Optional<User> user = userDao.findUserByUid(uid);
        if(user.isPresent()) {
        	userDao.delete(user.get());
        	result.status = true;
        	result.data = "success";
        	System.out.println(user.get());
        	response = new ResponseEntity<>(result, HttpStatus.OK);
        	
        }else {
        	result.status = false;
			result.data = "find Error";
			response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}