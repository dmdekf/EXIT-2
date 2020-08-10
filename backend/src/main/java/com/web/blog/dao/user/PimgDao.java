
package com.web.blog.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.web.blog.model.user.Pimg;
//User.java 참조 
public interface PimgDao extends JpaRepository<Pimg, String> {	
	Optional<Pimg> findById(String uid);
}
