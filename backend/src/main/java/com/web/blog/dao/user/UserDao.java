
package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
//User.java 참조 
public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);
    
    
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByUidAndUserkey(String uid,String userkey);


	Optional<User> findUserByUid(String nickname);
	Optional<User> findUserByEmail(String email);
	
}
