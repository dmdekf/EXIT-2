package com.web.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.user.User;

/**
 * UserService
 */
@Service
public class UserService {
	@Autowired
	UserDao userDao;
    // 로그인 계정은 고정값을 이용한다.
    public User signin(String email, String password) {
    	Optional<User> user = userDao.findUserByEmailAndPassword(email, password);
    	if(user.isPresent()) {
    		return user.get();
    	}else 
    		throw new RuntimeException("그런 사람은 없어요~");
//        if (email.equals("gyw8526@naver.com") && password.equals("duddnr122")) {
//            return new User(email, password);
//        } else {
//            throw new RuntimeException("그런 사람은 없어요~");
//        }
    }

    // 귀중한 정보를 전달한다.
    public String getServerInfo() {
        return "made by 은서파";
    }
}
 